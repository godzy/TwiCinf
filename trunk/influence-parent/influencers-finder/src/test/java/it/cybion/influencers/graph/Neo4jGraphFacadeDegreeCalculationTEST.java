package it.cybion.influencers.graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Neo4jGraphFacadeDegreeCalculationTEST {
	
	private static final Logger logger = Logger.getLogger(Neo4jGraphFacadeDegreeCalculationTEST.class);
	
	@Test (enabled=true)
	public void calculateInDegreeTEST() throws IOException, UserVertexNotPresent {
		String graphDirPath = "src/test/resources/graphs/calculateInDegreeTEST";
		delete(new File(graphDirPath));
		
		Neo4jGraphFacade graphFacade = new Neo4jGraphFacade(graphDirPath);
		
		long userId = 111;	
		graphFacade.addUser(userId);
		Assert.assertTrue( graphFacade.getUserVertex(userId) != null);
		
		List<Long> followersIds = new ArrayList<Long>();
		followersIds.add(222l);	
		followersIds.add(333l);
		followersIds.add(444l);
		graphFacade.addFollowers(userId, followersIds);
		
		Assert.assertEquals(graphFacade.getVerticesCount(), (1+followersIds.size()) );
		
		List<Long> usersToBeCalculated = new ArrayList<Long>();
		usersToBeCalculated.add(userId);
		graphFacade.calculateInDegree(usersToBeCalculated, followersIds);
		int inDegree = graphFacade.getInDegree(userId);
		Assert.assertEquals(inDegree, followersIds.size());
		
		delete(new File(graphDirPath));
	}
	
	
	@Test (enabled=true)
	public void calculateOutDegreeTEST() throws IOException, UserVertexNotPresent {
		String graphDirPath = "src/test/resources/graphs/calculateOutDegreeTEST";
		delete(new File(graphDirPath));
		
		Neo4jGraphFacade graphFacade = new Neo4jGraphFacade(graphDirPath);
		
		long userId = 111;	
		graphFacade.addUser(userId);
		Assert.assertTrue( graphFacade.getUserVertex(userId) != null);
		
		List<Long> followersIds = new ArrayList<Long>();
		followersIds.add(222l);	
		followersIds.add(333l);
		followersIds.add(444l);
		graphFacade.addFollowers(userId, followersIds);
		
		Assert.assertEquals(graphFacade.getVerticesCount(), (1+followersIds.size()) );
		
		List<Long> destinationNodes = new ArrayList<Long>();
		destinationNodes.add(userId);
		
		graphFacade.calculateOutDegree(followersIds, destinationNodes);
		for (Long followerId : followersIds) {
			int outDegree = graphFacade.getOutDegree(followerId);
			Assert.assertEquals(outDegree, 1);
		}
		
		int outDegree = graphFacade.getOutDegree(userId);
		Assert.assertEquals(outDegree, 0);

		
		delete(new File(graphDirPath));
	}
	
	
	public static void delete(File file) throws IOException{
		if(file.isDirectory()){
			//directory is empty, then delete it
	    	if(file.list().length==0){	 
	    		   file.delete();
	    		   logger.info("Directory is deleted : "+ file.getAbsolutePath());
	    	}
	    	else{
	    		//list all the directory contents
	    		String files[] = file.list();
	 
	    		for (String temp : files) {
	    			//construct the file structure
	    			File fileDelete = new File(file, temp);
	    			//recursive delete
	    			delete(fileDelete);
	    		}
	    		//check the directory again, if empty then delete it
	        	if(file.list().length==0){
	        		file.delete();
	        	    logger.info("Directory is deleted : " + file.getAbsolutePath());
	        	}
	    	}	 
	    }
		else{
	    		//if file, then delete it
	    		file.delete();
	    		logger.info("File is deleted : " + file.getAbsolutePath());
	    	}
	    }
	
}
