package com.uw.adc;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * Created by Anurita on 12/6/2016.
 */
public class FindSentiment {
    static StanfordCoreNLP pipeline;

    public static void init() {
        pipeline = new StanfordCoreNLP("StanfordProp.properties");
    }

    public static int findSentiment(String tweet) {

        int mainSentiment = 0;
        if (tweet != null && tweet.length() > 0) {
            int longestSentiment = 0;
            Annotation annotation = pipeline.process(tweet);
            for (CoreMap coreMap : annotation
                    .get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = coreMap
                        .get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = coreMap.toString();
                if (partText.length() > longestSentiment) {
                    mainSentiment = sentiment;
                    longestSentiment = partText.length();
                }

            }
        }
        return mainSentiment;
    }
}
