package org.sentimenty;

import org.sentimenty.analysis.OpinionPhraseFinder;
import org.sentimenty.analysis.PhraseScore;
import org.sentimenty.analysis.pattern.OpinionScore;
import org.sentimenty.common.FeatureFileUtil;
import org.sentimenty.common.POSUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by serkan on 17.05.2015.
 */
public class Analyzer {

    public static void main(String[] args) throws IOException {
        String review = null;
        double topNFeature = 0.8d;
        String category = null;
        // Analysis
        String taggedReview = POSUtil.tag(review);
        List<String> features = FeatureFileUtil.selectFeatureSet(category, topNFeature);
        List<OpinionScore> opinionScores = OpinionPhraseFinder.scoreFeatures(taggedReview, features);
        System.out.println(opinionScores);

        // TODO Find Turney's patterns calculate opinion orientation through PMI-IR
        // TODO persist score for trend analysis and to calculate success rate
    }
}
