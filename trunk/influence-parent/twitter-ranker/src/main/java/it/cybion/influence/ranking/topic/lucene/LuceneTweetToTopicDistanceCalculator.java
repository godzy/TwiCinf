package it.cybion.influence.ranking.topic.lucene;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;


public class LuceneTweetToTopicDistanceCalculator
{	
	private static final Logger logger = Logger.getLogger(LuceneTweetToTopicDistanceCalculator.class);
	
	private static final String LUCENE_ESCAPE_CHARS = "[\\\\+\\-\\!\\(\\)\\:\\^\\]\\{\\}\\~\\*\\?]";
	private static final Pattern LUCENE_PATTERN = Pattern.compile(LUCENE_ESCAPE_CHARS);
	private static final String REPLACEMENT_STRING = "\\\\$0";
	
	private Directory index;
	private IndexReader indexReader;
	private int docsCount;
	private StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
	
	public LuceneTweetToTopicDistanceCalculator(String indexPath,
												String topicBigDoc,
												List<String> outOfTopicBigDocs)
	{	
		index = createIndex(indexPath, topicBigDoc, outOfTopicBigDocs);
		docsCount = 1 + outOfTopicBigDocs.size();
		try
		{
			indexReader = IndexReader.open(index);
		}
		catch (CorruptIndexException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	public float getTweetToTopicDistance(String tweetText)
	{
		logger.info(tweetText);
		
		Query query = null;
		QueryParser queryParser = new QueryParser(Version.LUCENE_36, "content", analyzer);
		String cleanedTweetText = getCleanedTweetText(tweetText);
		try
		{
			query = queryParser.parse(cleanedTweetText);
		} catch (ParseException e1)
		{
			logger.info("Parsing error! Can't parse: "+cleanedTweetText);
			return 0;
		}
		int hitsPerPage = docsCount;
		IndexSearcher searcher = new IndexSearcher(indexReader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		try
		{
			searcher.search(query, collector);
		} catch (IOException e)
		{
			e.printStackTrace();
			logger.info("Problem with searcher.search(query, collector). Query=" + query);
		}
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		try
		{
			searcher.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		float score = 0;
		if (hits.length == 0)
			score = 0;
		else
		{
			for (int i=0; i<hits.length; i++)
			{
				ScoreDoc scoreDoc = hits[i];
				int docId = scoreDoc.doc;
				Document document = null;
				try
				{
					document = indexReader.document(docId);
					String inTopicString = document.get("inTopic");
					if (inTopicString.equals("true"))
					{
						logger.info("inTopic - score:"+scoreDoc.score);
					}
					if (inTopicString.equals("false"))
					{
						logger.info("outOfTopic - score:"+scoreDoc.score);
					}
				}
				catch (CorruptIndexException e)
				{
					e.printStackTrace();
					System.exit(0);
				}
				catch (IOException e)
				{
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
			
		return score;
	}
	
	
	private static Directory createIndex(String indexPath, String topicBigDoc, List<String> outOfTopicBigDocs)
	{
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, analyzer);
		Directory index = null;
		try
		{
			index = new SimpleFSDirectory(new File(indexPath));
		} catch (IOException e1)
		{
			e1.printStackTrace();
			System.exit(0);
		}
		IndexWriter indexWriter = null;
		try
		{
			indexWriter = new IndexWriter(index, config);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		addInTopicDocument(indexWriter, topicBigDoc);
		for (String outOfTopicBigDoc : outOfTopicBigDocs)
			addOutOfTopicDocument(indexWriter, outOfTopicBigDoc);
		try
		{
			indexWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		return index;
	}

	private static void addInTopicDocument(IndexWriter indexWriter, String bigDocument)
	{
		Document document = new Document();
		document.add(new Field("content", bigDocument, Field.Store.YES, Field.Index.ANALYZED));
		document.add(new Field("inTopic", "true", Field.Store.YES, Field.Index.NOT_ANALYZED));
		try
		{
			indexWriter.addDocument(document);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private static void addOutOfTopicDocument(IndexWriter indexWriter, String bigDocument)
	{
		Document document = new Document();
		document.add(new Field("content", bigDocument, Field.Store.YES, Field.Index.ANALYZED));
		document.add(new Field("inTopic", "false", Field.Store.YES, Field.Index.NOT_ANALYZED));
		try
		{
			indexWriter.addDocument(document);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	private String getCleanedTweetText(String originalTweetText)
	{
		String cleanedTweet = LUCENE_PATTERN.matcher(originalTweetText).replaceAll(REPLACEMENT_STRING);
		cleanedTweet = QueryParser.escape(cleanedTweet);
		return cleanedTweet;
	}
}
