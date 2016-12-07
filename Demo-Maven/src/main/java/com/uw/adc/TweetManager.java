package com.uw.adc;

import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anurita on 12/4/2016.
 */
public class TweetManager {
    public static ArrayList<String> getTweets(String topic) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("2jJqG3L8PRLNkFwfOFiN9GZ6o")
                .setOAuthConsumerSecret("zWNJvcN29gOxTtQRHF2nYdqdpRbdksZAuckoovamfzqIvGQyQx")
                .setOAuthAccessToken("805276824808763392kDanv3AYB7aVeqLzNXofQMmNXxZLHxV")
                .setOAuthAccessTokenSecret("cYJdgFiDbIO8qRC6bMeMxva324UHUfICaKRBVo9RabE0S");
        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        Twitter twitter = twitterFactory.getInstance();
//        Twitter twitter = new TwitterFactory().getInstance();
        ArrayList<String> tweetList = new ArrayList<String>();
        try {
            Query query = new Query(topic);
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
