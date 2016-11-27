package com.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNSmodel {
	 /*"Type" : "SubscriptionConfirmation",
	  "MessageId" : "094d320d-85b8-4509-a9e7-b3e07cc7f879",
	  "Token" : "2336412f37fb687f5d51e6e241d44a2dc0dc1be34b0d13b93614009cbca6e61b7208e855052dcb1eacbd5bc0ee193304da3048f97086f6d8c7ed0bd3bc4dd374521d4dd08a29c04e72cd62099f0c407c57bfc1ad3f29be628a4696165e18e7b4e4e35a1c1bb9f86aa3c6a7cbec3cec59",
	  "TopicArn" : "arn:aws:sns:us-west-2:940369824216:twitter",
	  "Message" : "You have chosen to subscribe to the topic arn:aws:sns:us-west-2:940369824216:twitter.\nTo confirm the subscription, visit the SubscribeURL included in this message.",
	  "SubscribeURL"*/
	
	@JsonProperty("Type")
	private String Type;
	@JsonProperty("MessageId")
	private String MessageId;
	@JsonProperty("Token")
	private String Token;
	@JsonProperty("TopicArn")
	private String TopicArn;
	@JsonProperty("Message")
	private String Message;
	@JsonProperty("SubscribeURL")
	private String SubscribeURL;
	
	
	public String getType() {
		return this.Type;
	}
	public void setType(String type) {
		this.Type = type;
	}
	public String getMessageId() {
		return this.MessageId;
	}
	public void setMessageId(String messageId) {
		this.MessageId = messageId;
	}
	public String getToken() {
		return this.Token;
	}
	public void setToken(String token) {
		this.Token = token;
	}
	public String getTopicArn() {
		return this.TopicArn;
	}
	public void setTopicArn(String topicArn) {
		this.TopicArn = topicArn;
	}
	public String getMessage() {
		return this.Message;
	}
	public void setMessage(String message) {
		this.Message = message;
	}
	public String getSubscribeURL() {
		return this.SubscribeURL;
	}
	public void setSubscribeURL(String subscribeURL) {
		this.SubscribeURL = subscribeURL;
	}
	
	

}
