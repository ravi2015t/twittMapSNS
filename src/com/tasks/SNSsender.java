package com.tasks;

import java.util.Properties;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.daemonservices.TweetMapExecutor;

	// Example SNS Sender
	public class SNSsender {

	    // AWS credentials -- replace with your credentials
	    private static final Properties awsCredentialsFile = TweetMapExecutor.
				getPropertiesFile("AwsCredentials.properties");

	    // Sender loop
	    public void snsSend(String message) throws Exception {

	        // Create a client
	    	BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsCredentialsFile.getProperty("accessKey"), awsCredentialsFile.getProperty("secretKey"));

	        try{
	        	AmazonSNSClient service = new AmazonSNSClient(awsCreds);
	        	Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	        	service.setRegion(usWest2);
	       
	            // Publish to a topic
	            PublishRequest publishReq = new PublishRequest()
	                .withTopicArn("arn:aws:sns:us-west-2:940369824216:twitter")
	                .withMessage(message
	                );
	            service.publish(publishReq);
	            System.out.println("Successfully published Request");
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }	    }
	}

