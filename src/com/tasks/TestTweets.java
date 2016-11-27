/*package com.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.daemonservices.ElasticSearchHose;
import com.daemonservices.TweetMapExecutor;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.models.Tweet;
import com.tasks.FetchTweetsTask.Location;

public class TestTweets {
	private static final Properties twiterProp = TweetMapExecutor.
			getPropertiesFile("TwitterConfig.properties");
	static List<String> tracks = new ArrayList<String>();
	
	private static Response connectWithTwitter() {
		OAuthService service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey(TestTweets.twiterProp.getProperty("consumer-key"))
                .apiSecret(TestTweets.twiterProp.getProperty("consumer-secret"))
                .build();

        Token accessToken = new Token(TestTweets.twiterProp.getProperty("access-token"), 
        		TestTweets.twiterProp.getProperty("access-secret"));

        System.out.println("Trying to plea Twitter for Tweets....");
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://stream.twitter.com/1.1/statuses/filter.json");
        request.addHeader("version", "HTTP/1.1");
        request.addHeader("host", "stream.twitter.com");
        request.setConnectionKeepAlive(true);
        request.addHeader("user-agent", "Twitter Stream Reader");
        request.addBodyParameter("track", convertArrayToDelimiterSepString(','));
        service.signRequest(accessToken, request);
        Response response = request.send();
        
        if(response != null) {
        	System.out.println("Connection Established...");
        }
        return response;
	}
	private static String convertArrayToDelimiterSepString(char delimiter) {
		StringBuilder sb = new StringBuilder();
		for(String s : tracks) {
			sb.append(s);
			sb.append(delimiter);
		}
		return sb.substring(0, sb.length()-1).toString();
	}
	public static void main(String[] args) {
		tracks.add("trump");
		Response response = connectWithTwitter();

        BufferedReader br = new BufferedReader(new InputStreamReader(response.getStream()));
        String tweetMessage;
      //  ElasticSearchHose hose = new ElasticSearchHose();
       // QueueService serv = new QueueService();
        try {
			while ((tweetMessage = br.readLine()) != null) {
				// check for the filters for geoLocation change processJson
				System.out.println("Message" + tweetMessage);
				//serv.sendtoQ(tweetMessage);
				 Tweet t = processJson(tweetMessage); 
				 if(t!=null)
				 {
		         System.out.println("tweet" + t.getTweet());
				 }
			}
		} catch ( Exception e) {
			e.printStackTrace();
		}

	}
	private static Tweet processJson(String message) {
		Tweet t = new Tweet();
		// Check for text
		JsonValue value = Json.parse(message);
		if(value != null && !value.isNull() && value.isObject()) {
			JsonObject obj = value.asObject();
			if(obj != null && !obj.isNull() && !obj.isEmpty()) {				
				String tweetText = obj.getString("text", "null");
				System.out.println("COMPUTED" + tweetText);
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

	private static Location lookForAppLocation(JsonObject obj) {
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
*/