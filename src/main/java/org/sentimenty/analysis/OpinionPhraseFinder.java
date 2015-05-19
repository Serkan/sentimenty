package org.sentimenty.analysis;

import org.apache.http.annotation.ThreadSafe;
import org.sentimenty.analysis.pattern.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by serkan on 18.05.2015.
 */
@ThreadSafe
public final class OpinionPhraseFinder {

    private static PatternHandler[] handlers = {
            new Pattern1(),
            new Pattern2(),
            new Pattern3(),
            new Pattern4(),
            new Pattern5(),
            new Pattern6(),
            new Pattern7()
    };


    public static List<OpinionScore> scoreFeatures(String taggedReview, List<String> features) {
        return Arrays
                .asList(handlers)
                .stream().map(h -> h.scoreFeatures(taggedReview, features))
                .flatMap(l -> l.stream())
                .collect(Collectors.toList());
    }
}
