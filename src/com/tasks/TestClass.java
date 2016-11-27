/*package com.tasks;

import java.io.IOException;
import java.util.Properties;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.daemonservices.TweetMapExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Tweet;

public class TestClass {

	public static void main(String[] args) {
              
		 Properties awsCredentialsFile = TweetMapExecutor.
				getPropertiesFile("AwsCredentials.properties");
		 BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJAVBK6KESJJMDNNA", "mkb+zqQBHd0NoUEaEY43f5sl28Ua4czbSPuy3a3o");
	        

	        AmazonSQS sqs = new AmazonSQSClient(awsCreds);
	        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	        sqs.setRegion(usWest2);

	        System.out.println("===========================================");
	        System.out.println("Getting Started with Amazon SQS");
	        System.out.println("===========================================\n");
	        try {
	              String myQueueUrl = awsCredentialsFile.getProperty("myQueueUrl");
	              System.out.println("q url" + myQueueUrl);
	              
                  String t = "ravi";	            
	            // Send a message
                  SendMessageRequest sendMessageRequest = new SendMessageRequest(myQueueUrl, "This is my message text.");
                  // You must provide a non-empty MessageGroupId when sending messages to a FIFO queue
       //           sendMessageRequest.setMessageGroupId("messageGroup1");
                  // Uncomment the following to provide the MessageDeduplicationId
                  //sendMessageRequest.setMessageDeduplicationId("1");
                  SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);
                 // String sequenceNumber = sendMessageResult.getSequenceNumber();
                  String messageId = sendMessageResult.getMessageId();
                  System.out.println("SendMessage succeed with messageId " + messageId );
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
	    }


	}


*/