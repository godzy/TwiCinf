package utils;

import it.cybion.influencers.cache.TwitterCache;
import it.cybion.influencers.cache.web.exceptions.ProtectedUserException;
import it.cybion.influencers.ranking.model.Tweet;
import it.cybion.influencers.ranking.utils.TweetsDeserializer;
import it.cybion.influencers.ranking.utils.urlsexpansion.UrlsExapandedTweetsTextExtractor;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;

public class TweetsGetter
{
	public static List<String> getUrlsExpandedTweetsTexts(long userId, TwitterCache twitterCache) throws TwitterException, ProtectedUserException
	{
		List<String> jsonTweets = twitterCache.getLast200Tweets(userId);
		List<Tweet> tweets = TweetsDeserializer.getTweetsObjectsFromJsons(jsonTweets);
		tweets = UrlsExapandedTweetsTextExtractor.getUrlsExpandedTextTweets(tweets);
		List<String> urlsExpandedTexts = new ArrayList<String>();
		for (Tweet tweet : tweets)
		{
			urlsExpandedTexts.add(tweet.urlsExpandedText);
		}
		return urlsExpandedTexts;
	}
}
