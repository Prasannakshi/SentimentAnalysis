package com.uw.adc;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anurita on 12/6/2016.
 */
public class TwitterHandler {
    public static ArrayList<String> getTweets(String twitterTrending, Twitter twitter) {

        //Twitter twitter = new TwitterFactory().getInstance();
        ArrayList<String> tweetList = new ArrayList<String>();
        try {
            Query query = new Query(twitterTrending);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    tweetList.add(tweet.getText());
                }
            } while ((query = result.nextQuery()) != null);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        return tweetList;
    }
}
