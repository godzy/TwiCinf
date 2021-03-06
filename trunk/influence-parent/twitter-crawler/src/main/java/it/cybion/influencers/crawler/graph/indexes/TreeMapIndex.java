package it.cybion.influencers.crawler.graph.indexes;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import it.cybion.influencers.crawler.graph.exceptions.UserVertexNotPresentException;

import java.util.TreeMap;



public class TreeMapIndex implements GraphIndex
{

	TreeMap<Long, Object> userId2vertexId;

	public TreeMapIndex()
	{
		userId2vertexId = new TreeMap<Long, Object>();
	}

	@Override
	public void put(Long userId, Vertex vertex)
	{
		if (!userId2vertexId.containsKey(userId))
			userId2vertexId.put(userId, vertex.getId());
	}

	@Override
	public Vertex getVertex(Neo4jGraph graph, Long userId) throws UserVertexNotPresentException
	{
		if (userId2vertexId.containsKey(userId))
			return graph.getVertex(userId2vertexId.get(userId));
		else
			throw new UserVertexNotPresentException("Trying to get node for user with id=" + userId + " but node is not in the graph");
	}
}
