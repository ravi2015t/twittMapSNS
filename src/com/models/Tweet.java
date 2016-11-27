package com.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.searchbox.annotations.JestId;

public class Tweet {
	@JsonProperty("tweetId")
	@JestId
	private String tweetId;
	
	@JsonProperty("tweet")
	private String tweet;
	
	@JsonProperty("sentiment")	
	private String sentiment;
	
	@JsonProperty("latitude")	
	private double latitude;
	
	@JsonProperty("longitude")	
	private double longitude;
	
	public Tweet() {}
	public Tweet(String tweetId, String tweetText, double lat, double longi) {
		this.tweetId = tweetId;
		this.tweet = tweetText;
		this.latitude = lat;
		this.longitude = longi;
	}
	
	public String getTweetId() {
		return this.tweetId;
	}
	
	public void setTweetId(String textId) {
		this.tweetId = textId;
	}
	
	public String getTweet() {
		return this.tweet;
	}
	
	public void setTweet(String text) {
		this.tweet = text;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	
	public double getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(double lat) {
		this.latitude = lat;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(double longi) {
		this.longitude = longi;
	}
}
