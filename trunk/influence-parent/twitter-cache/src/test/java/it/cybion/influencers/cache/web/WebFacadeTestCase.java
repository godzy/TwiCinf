package it.cybion.influencers.cache.web;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.cybion.influencers.cache.model.Tweet;
import it.cybion.influencers.cache.utils.CalendarManager;
import it.cybion.influencers.cache.web.exceptions.ProtectedUserException;
import it.cybion.influencers.cache.web.exceptions.WebFacadeException;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import twitter4j.TwitterException;

import java.util.*;

import static com.mongodb.util.MyAsserts.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class WebFacadeTestCase
{

	private static final Logger LOGGER = Logger.getLogger(WebFacadeTestCase.class);

	private WebFacade webFacade;

	@BeforeClass
	public void init()
	{
        Token appToken = new Token("Bam9RwjprVxWJd8TdhwQOg",
                "q1wzu5hn9HjYhEvYvPPBSIHWcfHIYJZoGnRlnD14D0");

        List<Token> userTokens = new ArrayList<Token>();

        Token userToken = new Token("962689441-yrFTbTzI3nAQ9sIMLnxLexyLWGAfZzhXCosTwuWp",
                "elPwBu9NeAoGXunIl1wyPJDsSYgLWlFQXbXR8C2KQc");
        userTokens.add(userToken);

        //TODO remove absolute paths
        //		Token userToken0 = new Token("/home/godzy/tokens/token0.properties");
        //		userTokens.add(userToken0);
        //		Token userToken1 = new Token("/home/godzy/tokens/token1.properties");
        //		userTokens.add(userToken1);
        //		Token userToken2 = new Token("/home/godzy/tokens/token2.properties");
        //		userTokens.add(userToken2);
        //		Token userToken3 = new Token("/home/godzy/tokens/token3.properties");
        //		userTokens.add(userToken3);
        //		Token userToken4 = new Token("/home/godzy/tokens/token4.properties");
        //		userTokens.add(userToken4);
        //		Token userToken5 = new Token("/home/godzy/tokens/token5.properties");
        //		userTokens.add(userToken5);

        webFacade = WebFacade.getInstance(appToken, userTokens);
    }

	@Test(enabled = true)
	public void getUserJsonTEST() throws TwitterException
	{
		String user = webFacade.getUserJson(813286l); // BarackObama
        LOGGER.info(user);
		assertTrue(user.contains("Barack Obama"));
		assertTrue(user.contains("followers"));
		assertTrue(user.contains("friends"));
		assertTrue(user.contains("This account is run by"));
	}

	@Test(enabled = true)
	public void getLessThan5000FollowersIdsTEST() throws TwitterException
	{
		List<Long> followerIds = webFacade.getFollowersIds(58477550); // screenName=gifo77
		assertTrue(followerIds.size() > 2);
		assertTrue(followerIds.size() < 5000);
	}

	@Test(enabled = true)
	public void getMoreThan5000FollowersIdsTEST() throws TwitterException
	{
		List<Long> followerIds = webFacade.getFollowersIds(444712353); // screenName=ChiaraMaci
		assertTrue(followerIds.size() > 8000);
	}

	@Test(enabled = true)
	public void getLessThan5000FriendsIdsTEST() throws TwitterException
	{
		List<Long> friendIds = webFacade.getFriendsIds(58477550); // screenName=gifo77
		assertTrue(friendIds.size() > 2);
		assertTrue(friendIds.size() < 5000);
	}

	@Test(enabled = true)
	public void getFriendsIdsRepeatedRequestsTEST() throws TwitterException
	{
		List<Long> friendIds = null;
		for (int i = 0; i < 5; i++)
			friendIds = webFacade.getFriendsIds(58477550); // screenName=gifo77
		assertTrue(friendIds.size() > 2);
		assertTrue(friendIds.size() < 5000);
	}

	@Test(enabled = true)
	public void getMoreThan5000FriendsIdsTEST() throws TwitterException
	{
		List<Long> followerIds = webFacade.getFriendsIds(14831419); // screenName=mattuk
		assertTrue(followerIds.size() > 20000);
	}

	@Test(enabled = true)
	public void getUsersJsonsTEST() throws WebFacadeException {
		List<Long> followerIds = new ArrayList<Long>();
		for (long i = 0; i < 101; i++)
			followerIds.add(435668609 + i);
		webFacade.getUsersJsons(followerIds);
	}
		
	@Test(enabled = true)
	public void getUserTweetsWithMaxId() throws TwitterException, ProtectedUserException
	{
		List<String> tweets = webFacade.getTweetsWithMaxId(887469007L, -1);
		Assert.assertTrue(tweets.size()>0);
	}

    @Test(enabled = false)
    public void getUserTweetsWithMaxIdForever() throws TwitterException, ProtectedUserException
    {
        while(true) {
            List<String> tweets = webFacade.getTweetsWithMaxId(887469007L, -1);
            assertTrue(tweets.size() > 0);
            LOGGER.info("got tweets: " + tweets.size());
        }
    }

	@Test(enabled = true)
	public void getTweetsFromDate1() throws TwitterException, ProtectedUserException
	{
		long userId = 887469007L; //edoventurini
		Date fromDate = CalendarManager.getDate(2012, 12, 13);
		Date toDate   = CalendarManager.getDate(2012, 12, 15);
		SearchedByDateTweetsResultContainer resultContainer = webFacade.getTweetsByDate(userId,fromDate,toDate);
		List<String> tweets = resultContainer.getGoodTweets();
		assertEquals(tweets.size(), 2);
	}
	
	
	@Test(enabled = true)
	public void getTweetsFromDate2() throws TwitterException, ProtectedUserException
	{
		long userId = 517903407L; //profdalimonte
		Date fromDate = CalendarManager.getDate(2013, 2, 3);
		Date toDate   = CalendarManager.getDate(2013, 2, 5);
		SearchedByDateTweetsResultContainer resultContainer = webFacade.getTweetsByDate(userId,fromDate,toDate);
		List<String> tweets = resultContainer.getGoodTweets();
		Assert.assertEquals(tweets.size(),8);
		Collections.sort(tweets);
	}
	
	
	@Test(enabled = true)
	public void getTweetsFromDateUserWithError() throws TwitterException, ProtectedUserException{
		long userId = 94040214L;
		Date fromDate = CalendarManager.getDate(2013, 2, 1);
		Date toDate   = CalendarManager.getDate(2013, 2, 20);
		SearchedByDateTweetsResultContainer resultContainer = webFacade.getTweetsByDate(userId,fromDate,toDate);
		List<String> tweets = resultContainer.getGoodTweets();
		Assert.assertTrue(tweets.size()==0);
	}
	
	
	
	
	@Test(enabled = true)
	public void getTweetsFromDateCheckIfContainsDuplicates() throws TwitterException, ProtectedUserException
	{
		long userId = 813286L; //BarackObama
		Date fromDate = CalendarManager.getDate(2012, 12, 1);
		Date toDate   = CalendarManager.getDate(2013, 1, 1);
		SearchedByDateTweetsResultContainer resultContainer = webFacade.getTweetsByDate(userId,fromDate,toDate);
		List<String> tweetJsons = resultContainer.getGoodTweets();
		Set<String> tweetJsonsSet = new HashSet<String>(tweetJsons);
		Assert.assertTrue(tweetJsons.size()>20);
		Assert.assertEquals(tweetJsons.size(), tweetJsonsSet.size());
	}
	
	@Test(enabled = true)
	public void getTweetsFromDateCheckIfDateIsCorrect() throws TwitterException, ProtectedUserException
	{
		long userId = 813286L; //BarackObama
		Date fromDate = CalendarManager.getDate(2013, 1, 1);
		Date toDate   = CalendarManager.getDate(2013, 3, 1);
		SearchedByDateTweetsResultContainer resultContainer = webFacade.getTweetsByDate(userId,fromDate,toDate);
		List<String> tweetJsons = resultContainer.getGoodTweets();
		for (String tweetJson : tweetJsons)
		{
			Gson gson = new GsonBuilder()
						.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
									// "Wed Oct 17 19:59:40 +0000 2012"
						.setDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy").create();
			Tweet tweet = gson.fromJson(tweetJson, Tweet.class);
			Assert.assertTrue(tweet.getCreatedAt().compareTo(fromDate)>0);
			Assert.assertTrue(tweet.getCreatedAt().compareTo(toDate)<0);			
		}
	}
	
	@Test(enabled = true)
	public void getTweetsFromProtectedUser() throws TwitterException
	{	
		try
		{
			webFacade.getTweetsWithMaxId(107684088, -1); //user:LesaMcMaster
			Assert.assertTrue(false);
		}
		catch (ProtectedUserException e)
		{
			Assert.assertTrue(true);
		} 	
	}
	
	
	@Test(enabled = true)
	public void getTweetsByDateDoesNotStop() throws TwitterException, ProtectedUserException
	{	
		Date fromDate = CalendarManager.getDate(2013, 2, 1);
		Date toDate   = CalendarManager.getDate(2013, 2, 20);
		long userId = 228432756;
		SearchedByDateTweetsResultContainer results = webFacade.getTweetsByDate(userId, fromDate, toDate);
		Assert.assertTrue(results.getGoodTweets().size()>10);
	}
	
	@Test(enabled = true)
	public void getTweetsByDateZeroTweets() throws TwitterException, ProtectedUserException
	{	
		Date fromDate = CalendarManager.getDate(2013, 2, 1);
		Date toDate   = CalendarManager.getDate(2013, 2, 20);
		long userId = 24767201; //user=wwdcareers
		SearchedByDateTweetsResultContainer results = webFacade.getTweetsByDate(userId, fromDate, toDate);
		Assert.assertTrue(results.getGoodTweets().size()==0);
	}
	
	

}
