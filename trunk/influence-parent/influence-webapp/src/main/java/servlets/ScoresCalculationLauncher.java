package servlets;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import it.cybion.influencers.cache.TwitterCache;
import it.cybion.influencers.cache.persistance.PersistenceFacade;
import it.cybion.influencers.cache.persistance.exceptions.PersistenceFacadeException;
import it.cybion.influencers.cache.persistance.exceptions.UserNotPresentException;
import it.cybion.influencers.cache.utils.CalendarManager;
import it.cybion.influencers.cache.web.Token;
import it.cybion.influencers.cache.web.WebFacade;
import it.cybion.influencers.ranking.RankedUser;
import it.cybion.influencers.ranking.RankingCalculator;
import it.cybion.influencers.ranking.topic.TopicScorer;
import it.cybion.influencers.ranking.topic.knn.KnnTopicScorer;
import it.cybion.model.twitter.User;
import org.apache.log4j.Logger;
import servlets.model.InfluenceUser;
import utils.PropertiesLoader;
import utils.UserFollowersExclusionStrategyC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * Servlet implementation class ScoresCalculator
 */
public class ScoresCalculationLauncher extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(ScoresCalculationLauncher.class);

    private PersistenceFacade persistenceFacade;

    private TwitterCache twitterCache;

    private Gson gson;

    private Properties properties;

    private PropertiesLoader pl;

    public ScoresCalculationLauncher() {

        super();
        LOGGER.info("created servlet");
    }

    @Override
    public void init() throws ServletException {

        this.pl = new PropertiesLoader();
        this.properties = this.pl.loadGeneralProperties();

        String pattern = "EEE MMM dd hh:mm:ss Z y";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        this.gson = new GsonBuilder().setExclusionStrategies(
                new UserFollowersExclusionStrategyC()).setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setDateFormat(
                simpleDateFormat.toPattern()).create();

        final String mongodbHost = this.properties.getProperty("mongodb_host");
        final String mongodbTwitterDb = this.properties.getProperty("mongodb_db");

        LOGGER.info("mongodb host " + mongodbHost);
        LOGGER.info("mongodb db " + mongodbTwitterDb);

        try {
            this.persistenceFacade = PersistenceFacade.getInstance(mongodbHost, mongodbTwitterDb);
        } catch (PersistenceFacadeException e) {
            final String emsg =
                    "persistence facade exception - unknown host: " + mongodbHost + " - " +
                    e.getMessage() + "";
            throw new ServletException(emsg, e);
        }

        try {
            this.twitterCache = initTwitterCache();
        } catch (PersistenceFacadeException e) {
            String emsg = "can't initialise twitter cache";
            throw new ServletException(emsg, e);
        }

        LOGGER.info("servlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //start parse param from request
        String usersListFilePath = request.getParameter("usersListFilePath");
        LOGGER.info("usersListFilePath=" + usersListFilePath);
        List<Long> usersToRank = parseUsersIdsFromFile(usersListFilePath);

        Date fromDate = getFromDate(request);
        LOGGER.info("fromDate=" + fromDate);

        Date toDate = getToDate(request);
        LOGGER.info("toDate=" + toDate);

        List<String> topicTweets = getTopicTweets(request);
        LOGGER.info("In Topic Tweets:");
        int count = 0;
        for (String tweet : topicTweets) {
            int i = count++;
            LOGGER.debug(i + ") " + tweet);
        }

        List<String> outOfTopicTweets = getOutOfTopicTweets(request);
        LOGGER.info("Out of Topic Tweets:");
        count = 0;
        for (String tweet : outOfTopicTweets) {
            int i = count++;
            LOGGER.debug(i + ") " + tweet);
        }

        int tweetsPerDocument = getTweetsPerDocument(request);
        LOGGER.info("tweetsPerDocument=" + tweetsPerDocument);

        int k = getK(request);
        LOGGER.info("k=" + k);

        //end parse param from request

        //build the ranking calculator
        final TopicScorer topicScorer = initKnnTopicScorer(topicTweets, outOfTopicTweets, k);
        final RankingCalculator rankingCalculator = new RankingCalculator(this.twitterCache,
                topicScorer);

        //use it
        final List<RankedUser> rankedUsers = rankingCalculator.getRankedUsersWithoutUrlsResolution(
                usersToRank, fromDate, toDate);

        LOGGER.info("found users: " + rankedUsers.size());

        final List<InfluenceUser> influenceUsers = new LinkedList<InfluenceUser>();

        for (RankedUser currentUser : rankedUsers) {

            final String screenName = currentUser.getScreenName();
            LOGGER.info("loading user by screen name '" + screenName + "'");
            final User fromPersistence = loadUserByScreenName(screenName);
            final InfluenceUser influencer = new InfluenceUser(currentUser, fromPersistence);
            influenceUsers.add(influencer);
        }


        //serialize in json
        final String influencersAsJson = this.gson.toJson(influenceUsers,
                new TypeToken<List<InfluenceUser>>() {
                }.getType());

        //write results to file
        final String influencersFilename = "influencers-" + UUID.randomUUID().toString() + ".json";
        final String influencersFilePath =
                this.pl.getInfluencersResultsDirectory() + influencersFilename;

        LOGGER.info(
                "writing rankedUsers '" + influencersAsJson + "' to file " + influencersFilePath);

        LOGGER.info("influencers to be serialized in json " + influenceUsers.size());

        try {
            writeStringToFile(influencersFilePath, influencersAsJson);
        } catch (IOException e) {
            throw new ServletException(
                    "cant write to file '" + influencersFilePath + "' these contents: '" +
                    influencersAsJson + "'", e);
        }

        LOGGER.info("wrote file: '" + influencersFilePath + "' - dispatching to view");

        request.setAttribute("influencersFilePath", influencersFilePath);
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher(
                "influencers-result.jsp");
        requestDispatcher.forward(request, response);
    }

    private User loadUserByScreenName(String screenName) {

        String userString = "";
        try {
            userString = this.persistenceFacade.getUser(screenName);
        } catch (UserNotPresentException e) {
            LOGGER.error("cant find '" + screenName + "' in local persistence: " + e.getMessage());
            //TODO find reason why we don't have the profile locally. we should have it
        }

        User user = null;
        try {
            user = this.gson.fromJson(userString, User.class);
        } catch (JsonSyntaxException e) {
            LOGGER.error("cant deserialize json to user: '" + userString + "'");
        }

        return user;
    }

    private void writeStringToFile(String filenamePath, String fileContent) throws IOException {

        final File destinationFile = new File(filenamePath);
        Files.write(fileContent, destinationFile, Charsets.UTF_8);
    }

    private List<String> getOutOfTopicTweets(HttpServletRequest request) throws ServletException {

        String topicFile = request.getParameter("topicFile");

        List<String> listFiles = new ArrayList<String>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(topicFile));
        } catch (FileNotFoundException e) {
            final String emsg =
                    "missing file for out of topic tweets: '" + topicFile + "'" + e.getMessage();
            LOGGER.error(emsg);
            throw new ServletException(emsg, e);
        } catch (IOException e) {
            final String emsg =
                    "can't read file for out of topic tweets: '" + topicFile + "'" + e.getMessage();
            LOGGER.error(emsg);
            throw new ServletException(emsg);
        }

        for (Entry<Object, Object> propertyEntry : properties.entrySet()) {
            String key = (String) propertyEntry.getKey();

            if (key.startsWith("outOfTopic")) {
                listFiles.add((String) propertyEntry.getValue());
            }
        }

        List<String> tweets = new ArrayList<String>();
        for (String filtFile : listFiles) {
            tweets.addAll(parseTweetsFromFile(filtFile));
        }
        return tweets;
    }

    private List<String> getTopicTweets(HttpServletRequest request) throws ServletException {

        final String topicFile = request.getParameter("topicFile");
        final String fullFilePath = topicFile;
        List<String> listFiles = new ArrayList<String>();

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(fullFilePath));
        } catch (FileNotFoundException e) {
            LOGGER.error(
                    "missing file for in topic tweets: '" + fullFilePath + "'" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error(
                    "can't read file for in topic tweets: '" + fullFilePath + "'" + e.getMessage());
            e.printStackTrace();
        }

        for (Entry<Object, Object> propertyEntry : properties.entrySet()) {
            String key = (String) propertyEntry.getKey();
            if (key.startsWith("inTopic")) {
                listFiles.add((String) propertyEntry.getValue());
            }
        }

        List<String> tweets = new ArrayList<String>();
        for (String filtFile : listFiles) {
            tweets.addAll(parseTweetsFromFile(filtFile));
        }
        return tweets;
    }

    private KnnTopicScorer initKnnTopicScorer(final List<String> topicTweets,
                                              final List<String> outOfTopicTweets, int k) {

        // builds a new scorer for each request
        KnnTopicScorer topicScorer = new KnnTopicScorer(topicTweets, outOfTopicTweets, k);
        return topicScorer;
    }

    //it now resolves full paths using propertiesLoader
    private TwitterCache initTwitterCache() throws PersistenceFacadeException {

        final String consumerTokenFilename = this.properties.getProperty("application_token_path");
        final String consumerTokenFullPath = this.pl.getTokensDirectory() + consumerTokenFilename;

        final Token applicationToken = new Token(consumerTokenFullPath);
        final List<Token> userTokens = new ArrayList<Token>();

        int i = 0;
        String userTokenFilename;
        while ((userTokenFilename = this.properties.getProperty("user_token_" + i + "_path")) != null) {
            final String userTokenFullPath = this.pl.getTokensDirectory() + userTokenFilename;
            LOGGER.info("loading token from " + userTokenFullPath);
            userTokens.add(new Token(userTokenFullPath));
            i++;
        }
        WebFacade webFacade = WebFacade.getInstance(applicationToken, userTokens);

        return TwitterCache.getInstance(webFacade, this.persistenceFacade);
    }

    private int getTweetsPerDocument(HttpServletRequest request) {

        String tweetsPerDocumentString = request.getParameter("tweetsPerDocument");
        return Integer.parseInt(tweetsPerDocumentString);
    }

    private int getK(HttpServletRequest request) {

        String kString = request.getParameter("k");
        return Integer.parseInt(kString);
    }

    private Date getFromDate(HttpServletRequest request) {

        String fromDateyearString = request.getParameter("fromDateYear");
        String fromDateMonthString = request.getParameter("fromDateMonth");
        String fromDateDayString = request.getParameter("fromDateDay");
        int year = Integer.parseInt(fromDateyearString);
        int month = Integer.parseInt(fromDateMonthString);
        int day = Integer.parseInt(fromDateDayString);
        Date fromDate = CalendarManager.getDate(year, month, day);
        return fromDate;
    }

    private Date getToDate(HttpServletRequest request) {

        String toDateYearString = request.getParameter("toDateYear");
        String toDateMonthString = request.getParameter("toDateMonth");
        String toDateDayString = request.getParameter("toDateDay");
        int year = Integer.parseInt(toDateYearString);
        int month = Integer.parseInt(toDateMonthString);
        int day = Integer.parseInt(toDateDayString);
        Date toDate = CalendarManager.getDate(year, month, day);
        return toDate;
    }

    private List<Long> parseUsersIdsFromFile(String listFilePath) throws ServletException {

        List<Long> ids = new ArrayList<Long>();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(listFilePath));
            String currentLine = fileReader.readLine();
            while (currentLine != null) {
                LOGGER.debug(currentLine);
                long id = Long.parseLong(currentLine);
                ids.add(id);
                currentLine = fileReader.readLine();
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            String emsg = "cant find file '" + listFilePath + "'";
            throw new ServletException(emsg, e);
        } catch (IOException e) {
            String emsg = "cant read from file '" + listFilePath + "'";
            throw new ServletException(emsg, e);
        }
        return ids;
    }

    private List<String> parseTweetsFromFile(String listFilePath) throws ServletException {

        List<String> tweets = new ArrayList<String>();

        FileReader in = null;

        try {
            in = new FileReader(listFilePath);
        } catch (FileNotFoundException e) {
            String emsg = "cant find file '" + listFilePath + "'";
            throw new ServletException(emsg, e);
        }

        BufferedReader fileReader = new BufferedReader(in);

        String currentLine = null;

        try {
            currentLine = fileReader.readLine();
        } catch (IOException e) {
            throw new ServletException("cant read line from file '" + listFilePath + "'", e);
        }

        while (currentLine != null) {
            tweets.add(currentLine);
            try {
                currentLine = fileReader.readLine();
            } catch (IOException e) {
                throw new ServletException("cant read line from file '" + listFilePath + "'", e);
            }
        }

        try {
            fileReader.close();
        } catch (IOException e) {
            final String emsg = "cant close file '" + listFilePath + "'";
            throw new ServletException(e);
        }

        return tweets;
    }

}
