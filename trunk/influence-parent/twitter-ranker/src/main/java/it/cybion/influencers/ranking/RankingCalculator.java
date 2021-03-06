package it.cybion.influencers.ranking;

import it.cybion.influencers.cache.TwitterCache;
import it.cybion.influencers.cache.exceptions.TwitterCacheException;
import it.cybion.influencers.cache.web.exceptions.ProtectedUserException;
import it.cybion.influencers.ranking.model.Tweet;
import it.cybion.influencers.ranking.topic.TopicScorer;
import it.cybion.influencers.ranking.utils.TweetsDeserializer;
import it.cybion.influencers.ranking.utils.urlsexpansion.UrlsExapandedTweetsTextExtractor;
import org.apache.log4j.Logger;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class RankingCalculator
{
	private static final Logger LOGGER = Logger.getLogger(RankingCalculator.class);
	
	private TwitterCache twitterCache;
	private List<RankedUser> rankedUsers = new ArrayList<RankedUser>();
	private TopicScorer topicDistanceCalculator;
	
	
	public RankingCalculator(TwitterCache twitterCache,
							 TopicScorer topicDistanceCalculator)
	{
		this.twitterCache = twitterCache;
		this.topicDistanceCalculator = topicDistanceCalculator;
	}
			
	public List<RankedUser> getRankedUsersWithUrlsResolution(List<Long> usersToRank, Date fromDate, Date toDate)
	{
		int usersCount = 1;
		for (Long userId : usersToRank)
		{		
			LOGGER.info("with url resolution");
			LOGGER.info("Calculating rank for user " + (usersCount++) + "/" + usersToRank.size() +
                        " with id " + userId);
			List<String> tweetsJsons = getTweetsJsons(userId, fromDate, toDate);
			if (tweetsJsons.isEmpty())
				continue;
			
			RankedUser rankedUser = calculateRankWithUrlsResolution(tweetsJsons);	;
			rankedUsers.add(rankedUser);
					
			LOGGER.info("user:" + rankedUser.getScreenName() +
                        " - followers:" + rankedUser.getFollowersCount() +
                        " - originalTweets:" + rankedUser.getOriginalTweets() +
                        " - topicTweetsCount:" + rankedUser.getTopicTweetsCount() +
                        " - topicTweetsRatio:" + rankedUser.getTopicTweetsRatio() +
                        " - meanRetweetCount:" + rankedUser.getMeanRetweetsCount() +
                        " - rank:" + rankedUser.getRank());
		}
		Collections.sort(rankedUsers);
		return rankedUsers;
	}
	
	public List<RankedUser> getRankedUsersWithoutUrlsResolution(List<Long> usersToRank, Date fromDate, Date toDate)
	{
		int usersCount = 1;

        try {
            this.twitterCache.downloadUsersProfiles(usersToRank);
        } catch (TwitterCacheException e) {
            LOGGER.error("failed while updating user profiles");
        }

        for (Long userId : usersToRank)
		{
            LOGGER.info("without url resolution");
            LOGGER.info("Calculating rank for user "+(usersCount++)+"/"+usersToRank.size()+" with id "+userId);

			List<String> tweetsAsJsons = getTweetsJsons(userId, fromDate, toDate);
			if (tweetsAsJsons.isEmpty())
			{
				LOGGER.warn("User has 0 tweets, can't calculate rank!");
				continue;
			}		
			RankedUser rankedUser = calculateRankWithoutUrlsResolution(tweetsAsJsons);

            if (rankedUser != null) {
                rankedUsers.add(rankedUser);
                LOGGER.info("user:" + rankedUser.getScreenName() +
                            " - followers:" + rankedUser.getFollowersCount() +
                            " - originalTweets:" + rankedUser.getOriginalTweets() +
                            " - topicTweetsCount:" + rankedUser.getTopicTweetsCount() +
                            " - topicTweetsRatio:" + rankedUser.getTopicTweetsRatio() +
                            " - meanRetweetCount:" + rankedUser.getMeanRetweetsCount() +
                            " - rank:" + rankedUser.getRank());
            } else {
                LOGGER.warn("ranked user with userId '"+ userId +"' is null");
            }


		}
		Collections.sort(rankedUsers);
		return rankedUsers;
	}
		
	private RankedUser calculateRankWithoutUrlsResolution(List<String> tweetsJsons) {

        double inTopicWeightedRetweetsAccumulator = 0;
        double tweetsTopicProximity = 0;
        double rank;
        List<Tweet> tweets = TweetsDeserializer.getTweetsObjectsFromJsons(tweetsJsons);
        String userScreenName = tweets.get(0).user.screen_name;
        int followersCount = tweets.get(0).user.followers_count;

        List<Tweet> originalTweets = getOriginalTweets(tweets);
        if (originalTweets.isEmpty()) {
            return new RankedUser(userScreenName, followersCount, 0, 0, 0, 0, 0);
        }
        for (Tweet tweet : originalTweets) {
            String text = tweet.text;
            //			logger.info(text);
            //between 0.0 and 1.0
            double tweetTopicProximity = topicDistanceCalculator.getTweetToTopicDistance(text);
            int retweetCount = tweet.retweet_count;
            double v = tweetTopicProximity * retweetCount;
            inTopicWeightedRetweetsAccumulator = inTopicWeightedRetweetsAccumulator + v;
            tweetsTopicProximity = tweetsTopicProximity + tweetTopicProximity;
        }
        double meanRetweetCount = inTopicWeightedRetweetsAccumulator / originalTweets.size();
        double topicTweetsRatio = tweetsTopicProximity / originalTweets.size();
        rank = rankingFunction(tweetsTopicProximity, meanRetweetCount, followersCount,
                topicTweetsRatio);

        return new RankedUser(userScreenName, followersCount, originalTweets.size(),
                tweetsTopicProximity, topicTweetsRatio, meanRetweetCount, rank);
    }

	
	private RankedUser calculateRankWithUrlsResolution(List<String> tweetsJsons)
	{
		double retweetsAccumulator = 0;
		double topicTweetsCount = 0;
		double rank;
		List<Tweet> tweets = TweetsDeserializer.getTweetsObjectsFromJsons(tweetsJsons);
		String userScreenName = tweets.get(0).user.screen_name;
		int followersCount = tweets.get(0).user.followers_count;
		
		List<Tweet> originalTweets = getOriginalTweets(tweets);
		if (originalTweets.size()<1)
			return new RankedUser(userScreenName, followersCount, 0, 0, 0, 0, 0);
		originalTweets = UrlsExapandedTweetsTextExtractor.getUrlsExpandedTextTweets(originalTweets);
		
		for (Tweet tweet : originalTweets)
		{
			String text = tweet.urlsExpandedText;
//			logger.info(text);
			double topicProximity = topicDistanceCalculator.getTweetToTopicDistance(text);
			int retweetCount = tweet.retweet_count;
			retweetsAccumulator = retweetsAccumulator + (topicProximity*retweetCount);
			topicTweetsCount = topicTweetsCount + topicProximity;		
		}
		double meanRetweetCount = retweetsAccumulator;
		double topicTweetsRatio = topicTweetsCount / originalTweets.size();
		rank = rankingFunction(topicTweetsCount, meanRetweetCount, followersCount, topicTweetsRatio);
		return new RankedUser(userScreenName, followersCount, originalTweets.size(), topicTweetsCount, topicTweetsRatio, meanRetweetCount, rank);
	}

	private List<Tweet> getOriginalTweets(List<Tweet> tweets)
	{
		List<Tweet> originalTweets = new ArrayList<Tweet>();
		for (Tweet tweet : tweets)
			if (tweet.retweeted_status==null && tweet.in_reply_to_status_id_str==null)
				originalTweets.add(tweet);
		return originalTweets;
	}

	private List<String> getTweetsJsons(long userId, Date fromDate, Date toDate)
	{		
		List<String> tweetsJsons;
		try
		{
			tweetsJsons = twitterCache.getTweetsByDate(userId, fromDate, toDate);
		}
		catch (TwitterException e)
		{
//			e.printStackTrace();
			LOGGER.warn("Error with user with id " + userId + ". Skipped!: " + e.getMessage());
			tweetsJsons = Collections.emptyList();
		}
		catch (ProtectedUserException e)
		{
			LOGGER.warn("User with id " + userId + " is protected. Skipped!: " + e.getMessage() + "'");
			tweetsJsons = Collections.emptyList();
		}	
		return tweetsJsons;
	}
	
	
	
	
	private double rankingFunction(double topicTweetsCount, double meanRetweetCount, int followersCount, double topicTweetsRatio)
	{
//		double rank = Math.log10(followersCount) * Math.sqrt(topicTweetsCount) * meanRetweetCount * (topicTweetsRatio*100)*(topicTweetsRatio*100);
		double rank = meanRetweetCount * topicTweetsRatio / Math.log10(followersCount);
		return rank;
	}
	
	
}
