package org.sentimenty.analysis.pattern;

import java.util.regex.Pattern;

/**
 * Created by serkan on 19.05.2015.
 */
public class Pattern5 extends PatternHandler {
    @Override
    public Pattern pattern() {
        return Pattern.compile("[a-z]+/(JJ|JJR|JJS) [a-z]+/(NN|NNS) ");
    }

    @Override
    public String opinionPhrase(String[] taggedWords) {
        return taggedWords[0];
    }

    @Override
    public int featurePosition() {
        return 1;
    }
}
