package it.cybion.influence.downloader;


import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

import it.cybion.influence.model.User;

import java.util.ArrayList;
import java.util.List;


/*
 * Request handler for a single pair <application,user>
 */
public class RequestHandlerImpl implements RequestHandler {
	
	private Twitter twitter;

	public RequestHandlerImpl(Token applicationToken, Token userToken) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(applicationToken.getTokenString())
		  .setOAuthConsumerSecret(applicationToken.getSecretString())
		  .setOAuthAccessToken(userToken.getTokenString())
		  .setOAuthAccessTokenSecret(userToken.getSecretString())
		  .setJSONStoreEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();

	}
	
	/*
	 * BEWARE: this ignores pagination. It only get the first (up to) 5000 friends.
	 */
	@Override
	public List<String> getUpTo5000FriendsIds(String userScreenName) throws TwitterException {
		IDs ids = null;
		try {
			//TwitterResponse response = twitter.getFriendsIDs(userScreenName, -1);
			//RateLimitStatus rateLimit = response.getRateLimitStatus();
			ids = twitter.getFriendsIDs(userScreenName, -1);
			return longArrayToStringList(ids.getIDs());
		} catch (TwitterException e) {
            throw e;
		}		
	}
	
	
	/*
	 * BEWARE: this ignores pagination. It only get the first (up to) 5000 friends.
	 */
	@Override
	public List<String> getUpTo5000FollowersIds(String userScreenName) throws TwitterException {
		IDs ids = null;
		try {
			//TwitterResponse response = twitter.getFriendsIDs(userScreenName, -1);
			//RateLimitStatus rateLimit = response.getRateLimitStatus();
			ids = twitter.getFollowersIDs(userScreenName, -1);
			return longArrayToStringList(ids.getIDs());
		} catch (TwitterException e) {
            throw e;
		}		
	}
	
	private List<String> longArrayToStringList(long[] array) {
		List<String> list = new ArrayList<String>();
		for (long arrayElement : array)
			list.add(String.valueOf(arrayElement));
		return list;
	}
	
	
	@Override
	public int getLimit() throws TwitterException {
		try {
			return twitter.getRateLimitStatus().getRemainingHits();
		} catch (TwitterException e) {
			throw e;
		}
	}

	@Override
	public IDs getFriendsWithPagination(String userScreenName, long cursor) throws TwitterException {
		return twitter.getFriendsIDs(userScreenName, cursor);
	}

	@Override
	public IDs getFollowersWithPagination(String userScreenName, long cursor) throws TwitterException {
		return twitter.getFollowersIDs(userScreenName, cursor);
	}

	@Override
	public String getRawJsonUser(String userScreenName) throws TwitterException {
		 return DataObjectFactory.getRawJSON(twitter.showUser(userScreenName));
	}
	
	@Override
	public String getRawJsonUser(long userId) throws TwitterException {
		 return DataObjectFactory.getRawJSON(twitter.showUser(userId));
	}

	@Override
	public User getUser(String screenName) throws TwitterException {
		return new User(twitter.showUser(screenName));
	}
	
	
	@Override
	public User getUser(long userId) throws TwitterException {
		return new User(twitter.showUser(userId));
	}

	@Override
	public IDs getFollowersWithPagination(long userId, long cursor) throws TwitterException {
		return twitter.getFollowersIDs(userId, cursor);
	}
	
	@Override
	public IDs getFriendsWithPagination(long userId, long cursor) throws TwitterException {
		return twitter.getFriendsIDs(userId, cursor);
	}

	@Override
	public String getUserRawJson(long userId) throws TwitterException {
		return DataObjectFactory.getRawJSON(twitter.showUser(userId));
	}
	
	
}
