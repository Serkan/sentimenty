package org.sentimenty;

import edu.stanford.nlp.util.Pair;
import org.sentimenty.common.DirectoryTagger;
import org.sentimenty.training.ReviewCollector;
import org.sentimenty.training.feature.FeatureExtracter;

import java.io.IOException;

/**
 * Created by serkan on 18.05.2015.
 */
public class Trainer {

    public static void main(String[] args) throws IOException {
        // Training
        String appId = null;
        String category = null;
        // crawl reviews
        ReviewCollector collector = new ReviewCollector();
        collector.startCollecting(new Pair<>(appId, category));
        // tag reviews
        DirectoryTagger tagger = new DirectoryTagger();
        tagger.tagDirectory(category);
        // Extract features
        FeatureExtracter featureExtracter = new FeatureExtracter(category);
        featureExtracter.extract();
    }
}
