package it.cybion.influencers.graph;

import it.cybion.influencers.graph.index.IndexType;
import it.cybion.influencers.utils.FilesDeleter;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

public class Neo4jGraphFacadeScalability {
	
	private static final Logger logger = Logger.getLogger(Neo4jGraphFacadeScalability.class);
	
	private int USERS_COUNT = 1000000;
	
	
	@Test(enabled=false)
	public void massiveInsertionsWithLucene() throws IOException, UserVertexNotPresent {
		String graphDirPath = "src/test/resources/graphs/massiveInsertionsTEST";
		FilesDeleter.delete(new File(graphDirPath));		
		Neo4jGraphFacade graphFacade = new Neo4jGraphFacade(graphDirPath, IndexType.LUCENE_INDEX);
		int tenPercent = Math.round((float)USERS_COUNT/10);
		int percentCompleted = 0;
		long start = System.currentTimeMillis();
		for (long i=0; i<USERS_COUNT; i++) {
			if (i%tenPercent == 0) {
				logger.info("completed "+percentCompleted+"%");
				percentCompleted = percentCompleted + 10;
			}
			graphFacade.addUser(i);
		}	
		long end = System.currentTimeMillis();
		long time = end - start;
		logger.info("TIME = "+time/1000.0);
	}
	
	
	@Test(enabled=true )
	public void massiveInsertionsWithTreeMap() throws IOException, UserVertexNotPresent {
		String graphDirPath = "src/test/resources/graphs/massiveInsertionsTEST";
		FilesDeleter.delete(new File(graphDirPath));		
		Neo4jGraphFacade graphFacade = new Neo4jGraphFacade(graphDirPath, IndexType.TREEMAP);				
		int tenPercent = Math.round((float)USERS_COUNT/10);
		int percentCompleted = 0;
		long start = System.currentTimeMillis();
		for (long i=0; i<USERS_COUNT; i++) {
			if (i%tenPercent == 0) {
				logger.info("completed "+percentCompleted+"%");
				percentCompleted = percentCompleted + 10;
			}
			graphFacade.addUser(i);
		}		
		long end = System.currentTimeMillis();
		long time = end - start;
		logger.info("TIME = "+time/1000.0);
	}
	
	
}
