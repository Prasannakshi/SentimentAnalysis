package com.uw.adc;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;


public class TwitterHandler {
    public static ArrayList<String> getTweets(String twitterTrending, Twitter twitter) {

        ArrayList<String> tweetList = new ArrayList<String>();
        try {
            Query query = new Query(twitterTrending);
            QueryResult queryResult;
            do {
                queryResult = twitter.search(query);
                List<Status> tweets = queryResult.getTweets();
                for (Status tweet : tweets) {
                    tweetList.add(tweet.getText());
                }
            } while ((query = queryResult.nextQuery()) != null);
        } catch (TwitterException e) {
            e.printStackTrace();

        }
        return tweetList;
    }
}
