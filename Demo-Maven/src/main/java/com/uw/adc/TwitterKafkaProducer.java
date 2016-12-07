package com.uw.adc;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Properties;

/*
 * Created by Sonam Gupta 
 */


public class TwitterKafkaProducer {

	private static final String topic = "twitter-topic";


	public static void run(String consumerKey, String consumerSecret,
			String token, String secret) throws InterruptedException {
		int countNeutral=0;
		int countPositive=0;
		int countNeg = 0;
		String twitterTrending = "#Seattle";
		Properties properties = new Properties();
		properties.put("metadata.broker.list", "localhost:9092");
		properties.put("serializer.class", "kafka.serializer.StringEncoder");
		properties.put("client.id","camus");
		ProducerConfig producerConfig = new ProducerConfig(properties);
		kafka.javaapi.producer.Producer<String, String> producer = new kafka.javaapi.producer.Producer<String, String>(
				producerConfig);

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).
				setOAuthConsumerKey(consumerKey).
				setOAuthConsumerSecret(consumerSecret).
				setOAuthAccessToken(token).
				setOAuthAccessTokenSecret(secret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		ArrayList<String> tweets = TwitterHandler.getTweets(twitterTrending, twitter);
		KeyedMessage<String, String> message = null;
		FindSentimentNLP.init();
		for(String tweet : tweets) {
			int sentiment = FindSentimentNLP.findSentiment(tweet);
			String tweetWithSentiment = tweet + " : " + sentiment;
			System.out.println(tweetWithSentiment);
			switch (sentiment) {
				case 3:
					countNeutral++;
					break;
				case 1:
					countNeg++;
					break;
				case 2:
					countPositive++;
					break;
				default:
					System.out.println("No sentiment");

			}
			message = new KeyedMessage<String, String>(topic, tweetWithSentiment);
			producer.send(message);
		}
		String finalValues = "Neutral: " + countNeutral + " Positive: " + countPositive + " Negative: " + countNeg;
		message = new KeyedMessage<String, String>(topic, finalValues);
		producer.send(message);
	}

	public static void main(String[] args) {
		try {
			String ConsumerKey = "";
			String ConsumerSecret = "";

			String AccessToken	= "";
			String AccessTokenSecret = "";
			
			TwitterKafkaProducer.run(ConsumerKey, ConsumerSecret , AccessToken, AccessTokenSecret);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}
