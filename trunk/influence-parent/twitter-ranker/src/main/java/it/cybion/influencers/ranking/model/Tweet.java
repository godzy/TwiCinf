package it.cybion.influencers.ranking.model;


public class Tweet
{
	public String text;
	public String urlsExpandedText;
	public Entities entities;	
	public long id;
	public int retweet_count;
	public Tweet retweeted_status;	
	public User user;
	public String in_reply_to_status_id_str;
}
