package com.tasks;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//import org.codehaus.jackson.map.ObjectMapper;

import java.util.Map.Entry;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.daemonservices.TweetMapExecutor;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.models.Tweet;
import com.tasks.FetchTweetsTask.Location;

public class ReceiveQueue implements Runnable{
	private static final Properties awsCredentialsFile = TweetMapExecutor.
			getPropertiesFile("AwsCredentials.properties");
	BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsCredentialsFile.getProperty("accessKey"), awsCredentialsFile.getProperty("secretKey"));

    AmazonSQS sqs = getclient(awsCreds);
    String myQueueUrl = awsCredentialsFile.getProperty("myQueueUrl");
   
    
    public AmazonSQSClient getclient(BasicAWSCredentials awsCreds )
    {
    AmazonSQS sqsS = new AmazonSQSClient(awsCreds);
    Region usWest2 = Region.getRegion(Regions.US_WEST_2);
    sqsS.setRegion(usWest2);
    return (AmazonSQSClient) sqsS;
    	
    }
 
    SNSsender snsR = new SNSsender();
	@Override
	public void run() {
		System.out.println("URL" + myQueueUrl);
		
		System.out.println("Inside RUN");
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
        System.out.println("After initializing");
		
		AlchemyLanguage service = new AlchemyLanguage();
		service.setApiKey("5433cac3dd05edd063519c29736beee4d0b58bff");	
		for(;;)
		{
			List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
		if(messages.size()>0)
		{
        for (Message message : messages) {
           /* System.out.println("  Message");
            System.out.println("    MessageId:     " + message.getMessageId());
            System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
            System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
            System.out.println("    Body:          " + message.getBody());
           */ 
          try {
        	  
        	Tweet t = processJson(message.getBody()); 
            if(t!=null)
            {
    		Map<String,Object> params = new HashMap<String, Object>();
    		params.put(AlchemyLanguage.TEXT, t.getTweet());
    		DocumentSentiment sentiment = service.getSentiment(params).execute();
            t.setSentiment(sentiment.getSentiment().getType().toString());
            System.out.println("Getting Sentiment"+ sentiment.getSentiment().getType().toString());
            ObjectMapper mapper = new ObjectMapper();
           
				String jsonInString = mapper.writeValueAsString(t);
				snsR.snsSend(jsonInString);
				System.out.println("COmpleted sending to sns");
				
			} 
        	}catch (IOException e) {
				System.out.println("Failed while sending messages");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Failed while sending messages");
				e.printStackTrace();
			}
            }
        }
		
		else
		{
		 continue;
		}
		}
		}

	private Tweet processJson(String message) {
		Tweet t = new Tweet();
		// Check for text
		JsonValue value = Json.parse(message);
		if(value != null && !value.isNull() && value.isObject()) {
			JsonObject obj = value.asObject();
			if(obj != null && !obj.isNull() && !obj.isEmpty()) {				
				String tweetText = obj.getString("text", "null");
				String tweetId = obj.getString("id_str", "");
				t.setTweetId(tweetId);
				t.setTweet(tweetText);

				// Check for actual position
				JsonValue loc = obj.get("coordinates");
				if(loc != null && !loc.isNull() && loc.isObject()) {
					JsonObject locObj = loc.asObject();
					if(locObj != null && !locObj.isNull() && !locObj.isEmpty()) {
						JsonValue coordinates = locObj.get("coordinates");
						if(coordinates != null && !coordinates.isNull() && coordinates.isArray()) {
							JsonArray arr = coordinates.asArray();
							if(arr != null && !arr.isNull() && !arr.isEmpty()) {
								double longi = arr.get(0).asDouble();
								double lat = arr.get(1).asDouble();
								t.setLatitude(lat);
								t.setLongitude(longi);
								return t;
							}
						}
					}
				} else {
					Location appLoc = lookForAppLocation(obj);
					if(appLoc != null) {
						t.setLatitude(appLoc.latitude);
						t.setLongitude(appLoc.longitude);
						return t;
					}
					
				}
			}
		}
		return null;
}

	private Location lookForAppLocation(JsonObject obj) {
		Location appLoc = new Location();
		JsonValue loc = obj.get("place");
		if(loc != null && !loc.isNull() && loc.isObject()) {
			JsonObject locObj = loc.asObject();
			if(locObj != null && !locObj.isNull() && !locObj.isEmpty()) {
				JsonValue coordinates = locObj.get("bounding_box");
				if(coordinates != null && !coordinates.isNull() && coordinates.isObject()) {
					JsonObject boundedBox = coordinates.asObject();
					if(boundedBox != null && !boundedBox.isNull() && boundedBox.isObject()) {
						JsonValue val = boundedBox.get("coordinates");
						if(val != null && !val.isNull() && val.isArray()) {
							JsonArray o = val.asArray();
							JsonValue v = o.get(0);
							if(v != null && !v.isNull() && v.isArray()) {
								JsonArray temp = v.asArray();
								double[] longi = new double[4];
								double[] lati = new double[4];
								for(int i = 0; i < 4; i++) {
									JsonValue tup = temp.get(i);
									JsonArray tupArr = tup.asArray();
									longi[i] = tupArr.get(0).asDouble();
									lati[i] = tupArr.get(1).asDouble();
								}
								appLoc.latitude = (lati[0] + lati[2])/2;
								appLoc.longitude = (longi[0] + longi[2])/2;
								return appLoc;
							}
						}
					}
				}
			}
		}
		return null;
}

}
