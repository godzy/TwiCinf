package it.cybion.influencers.twitter.persistance;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import java.util.Map;
import java.util.HashMap;



public class MongodbPersistanceFacade implements PersistanceFacade {
	
	private static final Logger logger = Logger.getLogger(PersistanceFacade.class);

	private DBCollection userCollection;
	private DBCollection tweetsCollection;
	

	public MongodbPersistanceFacade(String host, String database) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient( host );
		DB db = mongoClient.getDB( database );
		this.userCollection = db.getCollection( "users" );
		this.userCollection.createIndex( new BasicDBObject("id", 1) ); //if the index already exist this does nothing
		this.tweetsCollection = db.getCollection( "tweets" );
		this.tweetsCollection.createIndex( new BasicDBObject("id", 1) ); //if the index already exist this does nothing
	}
	
	
	public void putTweet(String tweetToInsertJson) {
		DBObject tweetToInsert = (DBObject) JSON.parse(tweetToInsertJson);
		long tweetId = new Long((Integer) tweetToInsert.get("id"));
		logger.info("tweetId="+tweetId);
		try {
			getTweet(tweetId);
		} catch (TweetNotPresentException e) {
			logger.info("TweetNotPresentException catched");
			tweetsCollection.insert(tweetToInsert);
			return;
		}	
	}
	
	
	private String getTweet(Long tweetId) throws TweetNotPresentException {
		BasicDBObject keys = new BasicDBObject();
		keys.put("id", tweetId);
		DBObject tweet = userCollection.findOne(keys);
		if (tweet == null)
			throw new TweetNotPresentException("Tweet with id "+tweetId+" is not in the collection.");
		return tweet.toString();
	}
	
	public List<String> getTweets(Long userId) {
		BasicDBObject keys = new BasicDBObject();
		keys.put("\"user.id\"", userId);
		DBCursor cursor = userCollection.find(keys);
		logger.info(cursor);
		List<String> tweets = new ArrayList<String>();
		while (cursor.hasNext())
			tweets.add((String)cursor.next().get("text"));
		return tweets;
	}
	
	
	/*
	 * If a user with the same id (beware: id!=_id) is already present, 
	 * the new fields (if exist) are added.
	 */		
	@Override
	public void putUser(String userToInsertJson) {
		DBObject userToInsert = (DBObject) JSON.parse(userToInsertJson);
		long userId = new Long((Integer) userToInsert.get("id"));
		String userInDbJson;
		try {
			userInDbJson = getUser(userId);
		} catch (UserNotPresentException e) {
			userCollection.insert(userToInsert);
			return;
		}
		DBObject userInDb = (DBObject) JSON.parse(userInDbJson);
		Map<String, Object> field2value = new HashMap<String,Object>();		
		for (String field : userInDb.keySet())
			field2value.put(field, userInDb.get(field));
		for (String field : userToInsert.keySet())
			field2value.put(field, userToInsert.get(field));		
		DBObject updatedUser = new BasicDBObject();
		for (String field : field2value.keySet())
			updatedUser.put(field, field2value.get(field));		
		DBObject query = new BasicDBObject();
		query.put("id", userId);
		userCollection.update(query, updatedUser);		
	}

	@Override
	public void putFriends(Long userId, List<Long> friendsIds) throws UserNotPresentException{
		logger.info("writing "+friendsIds.size()+" friends for user with id="+userId);
		String userJson = getUser(userId);
		DBObject user = (DBObject) JSON.parse(userJson);
		user.put("friends", friendsIds);
		putUser(user.toString());
		for (Long friendId : friendsIds) {
			DBObject friend = new BasicDBObject();
			friend.put("id", friendId);
			putUser(friend.toString());
		}	
	}

	@Override
	public void putFollowers(Long userId, List<Long> followersIds) throws UserNotPresentException {
		logger.info("writing "+followersIds.size()+" followers for user with id="+userId);
		String userJson = getUser(userId);
		DBObject user = (DBObject) JSON.parse(userJson);
		user.put("followers", followersIds);
		putUser(user.toString());
		for (Long followerId : followersIds) {
			DBObject follower = new BasicDBObject();
			follower.put("id", followerId);
			putUser(follower.toString());
		}			
	}
	
	@Override
	public void removeUser(Long userId) {
		BasicDBObject keys = new BasicDBObject();
		keys.put("id", userId);
		DBCursor cursor = userCollection.find(keys);
		if (cursor.hasNext()) {	
			DBObject json = cursor.next();
			userCollection.remove(json);
		}
	}
	
	@Override
	public String getUser(Long userId) throws UserNotPresentException {
		BasicDBObject keys = new BasicDBObject();
		keys.put("id", userId);
		DBObject user = userCollection.findOne(keys);
		if (user==null)
			throw new UserNotPresentException("User with id "+userId+" is not in the collection.");
		return user.toString();
	}
		
	private DBObject getUserDbObject(Long userId) throws UserNotPresentException {
		BasicDBObject keys = new BasicDBObject();
		keys.put("id", userId);
		DBObject user = userCollection.findOne(keys);
		if (user==null)
			throw new UserNotPresentException("User with id "+userId+" is not in the collection.");
		return user;
	}

	@Override
	public String getDescription(Long userId) throws UserNotPresentException, UserNotProfileEnriched {
		String userJson = getUser(userId);
		DBObject user = (DBObject) JSON.parse(userJson);		
		if (!user.containsField("description"))
			throw new UserNotProfileEnriched("User with id "+userId+" is not profile-eniched.");
		String description = (String) user.get("description");
		if (description==null)
			description = "";
		return description;		
	}
	
	public String getStatus(Long userId)  throws UserNotPresentException, UserNotProfileEnriched {
		String userJson = getUser(userId);
		DBObject user = (DBObject) JSON.parse(userJson);		
		if (!user.containsField("description"))
			throw new UserNotProfileEnriched("User with id "+userId+" is not profile-eniched.");
		DBObject status = (DBObject) user.get("status");	
		String text = (String) status.get("text");
		if (text==null)
			text = "";
		return text;		
	}
		
	@Override
	public String getDescriptionAndStatus(Long userId) throws UserNotPresentException, UserNotProfileEnriched {
		String userJson = getUser(userId);
		DBObject user = (DBObject) JSON.parse(userJson);		
		if (!user.containsField("description"))
			throw new UserNotProfileEnriched("User with id "+userId+" is not profile-eniched.");
		String description = (String) user.get("description");
		DBObject statusObject = (DBObject) user.get("status");	
		String status = "";
		if (statusObject!=null)
			status = (String) statusObject.get("text");
		return description + " " + status;		
	}
		
	@Override
	public int getFollowersCount(Long userId) throws UserNotPresentException, UserNotProfileEnriched {
		String userJson = getUser(userId);
		DBObject user = (DBObject) JSON.parse(userJson);		
		if (!user.containsField("followers_count"))
			throw new UserNotProfileEnriched("User with id "+userId+" is not profile-eniched.");
		int followersCount = (Integer)user.get("followers_count");
		return followersCount;		
	}
	
	@Override
	public int getFriendsCount(Long userId) throws UserNotPresentException, UserNotProfileEnriched {
		String userJson = getUser(userId);
		DBObject user = (DBObject) JSON.parse(userJson);		
		if (!user.containsField("friends_count"))
			throw new UserNotProfileEnriched("User with id "+userId+" is not profile-eniched.");
		int followersCount = (Integer)user.get("friends_count");
		return followersCount;		
	}
	
	@Override
	public String getScreenName(Long userId) throws UserNotPresentException, UserNotProfileEnriched {
		String userJson = getUser(userId);
		DBObject user = (DBObject) JSON.parse(userJson);
		if (!user.containsField("screen_name"))
			throw new UserNotProfileEnriched("User with id "+userId+" is not profile-eniched.");
		String screenName = (String) user.get("screen_name");
		if (screenName==null)
			screenName = "";
		return screenName;			
	}

	@Override
	public List<Long> getFollowers(Long userId) throws  UserNotPresentException, UserNotFollowersEnrichedException {
		DBObject userJson = getUserDbObject(userId);		
		if (userJson.containsField("followers")) {	
			List<Integer> intList = (List<Integer>)userJson.get("followers");
			List<Long> longList = new ArrayList<Long>();
			for (int intElement : intList)
				longList.add( (long)intElement );
			return longList;
		}
		else
			throw new UserNotFollowersEnrichedException("User with id "+userId+" is not followers/friends-eniched.");			
	}

	@Override
	public List<Long> getFriends(Long userId) throws UserNotFriendsEnrichedException, UserNotPresentException {
		DBObject userJson = getUserDbObject(userId);
		if (userJson.containsField("friends")) {
			List<Integer> intList = (List<Integer>)userJson.get("friends");
			List<Long> longList = new ArrayList<Long>();
			for (int intElement : intList)
				longList.add( (long)intElement );
			return longList;
		}
		else
			throw new UserNotFriendsEnrichedException("User with id "+userId+" is not friends-eniched.");			
	}
	
}
