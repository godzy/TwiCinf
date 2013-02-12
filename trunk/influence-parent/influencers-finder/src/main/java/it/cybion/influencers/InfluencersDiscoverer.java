package it.cybion.influencers;


import it.cybion.influencers.filtering.FilterManager;
import it.cybion.influencers.graph.GraphFacade;
import it.cybion.influencers.twitter.TwitterFacade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;



public class InfluencersDiscoverer
{

	private static final Logger logger = Logger.getLogger(InfluencersDiscoverer.class);

	private int iterations;
	private List<Long> users;
	private GraphFacade graphFacade;
	private TwitterFacade twitterFacade;
	private List<FilterManager> toIterateFilters;
	private List<FilterManager> finalizationFilters;
	private Set<Long> resultsFromIterations = new HashSet<Long>();

	public InfluencersDiscoverer(int iterations, List<Long> users, GraphFacade graphFacade, TwitterFacade twitterFacade, List<FilterManager> toIterateFilters, List<FilterManager> finalizationFilters)
	{
		this.iterations = iterations;
		this.users = users;
		this.graphFacade = graphFacade;
		this.twitterFacade = twitterFacade;
		this.toIterateFilters = toIterateFilters;
		this.finalizationFilters = finalizationFilters;
	}

	public InfluencersDiscoverer(int iterations, List<Long> users, GraphFacade graphFacade, TwitterFacade twitterFacade, List<FilterManager> toIterateFilters)
	{
		this.iterations = iterations;
		this.users = users;
		this.graphFacade = graphFacade;
		this.twitterFacade = twitterFacade;
		this.toIterateFilters = toIterateFilters;
		this.finalizationFilters = null;
	}

	public List<Long> getInfluencers()
	{

		printInfo();

		for (int iterationIndex = 0; iterationIndex < iterations; iterationIndex++)
		{
			logger.info("");
			logger.info("");
			logger.info("#### ITERATION " + (iterationIndex + 1) + " #####");
			logger.info("");
			for (int filterIndex = 0; filterIndex < toIterateFilters.size(); filterIndex++)
			{
				FilterManager filterManager = toIterateFilters.get(filterIndex);
				logger.info("");
				logger.info("");
				logger.info("#### filter " + (filterIndex + 1) + "/" + toIterateFilters.size() + " ####");
				logger.info("");
				filterManager.setGraphFacade(graphFacade);
				filterManager.setTwitterFacade(twitterFacade);
				filterManager.setSeedUsers(users);
				logger.info(filterManager.toString());
				users = filterManager.filter();
				logger.info("results from filtering = " + users);
				logger.info("number of results from filtering = " + users.size());
			}
			resultsFromIterations.addAll(users);
		}

		if (finalizationFilters != null)
		{
			logger.info("");
			logger.info("results before finalization filters = " + resultsFromIterations);
			logger.info("results before finalization filters size = " + resultsFromIterations.size());
			logger.info("");
			logger.info("");
			logger.info("#### FINALIZING FILTERS #####");
			logger.info("");
			for (int filterIndex = 0; filterIndex < finalizationFilters.size(); filterIndex++)
			{
				FilterManager filterManager = finalizationFilters.get(filterIndex);
				logger.info("");
				logger.info("");
				logger.info("#### filter " + (filterIndex + 1) + "/" + toIterateFilters.size() + " ####");
				logger.info("");
				filterManager.setGraphFacade(graphFacade);
				filterManager.setTwitterFacade(twitterFacade);
				if (filterIndex == 0)
					filterManager.setSeedUsers(new ArrayList<Long>(resultsFromIterations));
				else
					filterManager.setSeedUsers(users);
				logger.info(filterManager.toString());
				users = filterManager.filter();
				logger.info("results from filtering = " + users);
				logger.info("number of results from filtering = " + users.size());
			}
		}

		return users;
	}

	private void printInfo()
	{
		logger.info("############################################");
		logger.info("Iterations =  " + iterations);
		logger.info("--Iteration Filters--");
		for (int filterIndex = 0; filterIndex < toIterateFilters.size(); filterIndex++)
		{
			FilterManager filterManager = toIterateFilters.get(filterIndex);
			logger.info(filterIndex + ") " + filterManager.toString());
		}

		if (finalizationFilters != null)
		{
			logger.info("--Finalization Filters--");
			for (int filterIndex = 0; filterIndex < finalizationFilters.size(); filterIndex++)
			{
				FilterManager filterManager = finalizationFilters.get(filterIndex);
				logger.info(filterIndex + ") " + filterManager.toString());
			}
		}
		logger.info("############################################");
		logger.info("");
		logger.info("");
	}
}
