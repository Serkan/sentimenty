package org.sentimenty.common.util;

import org.sentimenty.common.config.SentimentyConfig;
import org.sentimenty.common.search.SearchEngine;
import org.sentimenty.common.search.SearchEngineFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by serkan on 18.05.2015.
 */
public class PMIIRScoreCalculator {
    /*
    THIS CLASS IS NOT THREAD SAFE !!!
     */

    private static Map<String, Long> hitCache = new HashMap<>();

    private static long hit(String term) {
        if (hitCache.containsKey(term)) {
            return hitCache.get(term);
        } else {
            SearchEngine searchEngine = SearchEngineFactory.get(SentimentyConfig.SEARCH_ENGINE_ID);
            long l = searchEngine.hitCount(term);
            hitCache.put(term, l);
            return l;
        }
    }

    public static double calculate(String term1, String term2) {
        long mutualHit = hit(term1 + " " + term2);
        long term1Hit = hit(term1);
        long term2Hit = hit(term2);
        double score = -1d;
        try {
            long mltp = Math.multiplyExact(term1Hit, term2Hit);
            score = (Double.valueOf(mutualHit) / Double.valueOf(mltp));
        } catch (ArithmeticException e) {
            // swallow exception and leave score -1
        }
        // log base 'e'
        return Math.log(score);
    }


}
