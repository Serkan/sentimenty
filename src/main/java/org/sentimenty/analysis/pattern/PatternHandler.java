package org.sentimenty.analysis.pattern;

import org.sentimenty.common.util.PMIIRScoreCalculator;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by serkan on 19.05.2015.
 */
public abstract class PatternHandler {

    private static final String POS_REFERENCE_WORD = "excellent";

    private static final String NEG_REFERENCE_WORD = "poor";

    public abstract Pattern pattern();

    public abstract String opinionPhrase(String[] taggedWords);

    public abstract int featurePosition();

    public final List<OpinionScore> scoreFeatures(String taggedReview, List<String> features) {
        String[] taggedWords = taggedReview.split(" ");
        String feature = taggedWords[featurePosition()];
        List<OpinionScore> result = new LinkedList<>();
        if (features.contains(feature)) {
            String opinionPhrase = opinionPhrase(taggedWords);
            double score = score(opinionPhrase);
            // we obtain feature, opinion phrase, score
            OpinionScore opinionScore = new OpinionScore(feature, opinionPhrase, score);
            result.add(opinionScore);
        }
        return result;
    }

    private double score(String opinionPhrase) {
        // use turney's PMI-IR algorithm to find orientation
        double posPMI = PMIIRScoreCalculator.calculate(opinionPhrase, POS_REFERENCE_WORD);
        double negPMI = PMIIRScoreCalculator.calculate(opinionPhrase, NEG_REFERENCE_WORD);
        // SemanticOrientation(PMI(phrase, 'excellent') - PMI(phrase, 'poor'))
        return posPMI - negPMI;
    }
}
