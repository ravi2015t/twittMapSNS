/*package com.tasks;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.mortbay.jetty.Server;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.daemonservices.TweetMapExecutor;
import com.tasks.SNSReceiver.AmazonSNSHandler;

public class TestSNSReceiver {
	 private static final Properties awsCredentialsFile = TweetMapExecutor.
				getPropertiesFile("AwsCredentials.properties");
	
	public static void main(String args[])
	{
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsCredentialsFile.getProperty("accessKey"), awsCredentialsFile.getProperty("secretKey"));

        AmazonSNSClient service = new AmazonSNSClient(awsCreds);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
    	service.setRegion(usWest2);
   
        // Get an HTTP Port
        int port =  8909;

        // Create and start HTTP server
        Server server = new Server(port);
        server.setHandler(new AmazonSNSHandler());
        try {
			server.start();
		

        // Subscribe to topic
        SubscribeRequest subscribeReq;
	
			subscribeReq = new SubscribeRequest()
			    .withTopicArn("arn:aws:sns:us-west-2:940369824216:twitter")
			    .withProtocol("http")
			    .withEndpoint("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port);
			service.subscribe(subscribeReq);
	        System.out.println("After subscription");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}*/
