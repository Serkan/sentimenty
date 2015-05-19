package org.sentimenty.analysis.pattern;

import java.util.regex.Pattern;

/**
 * Created by serkan on 19.05.2015.
 */
public class Pattern3 extends PatternHandler {
    @Override
    public Pattern pattern() {
        return Pattern.compile("[a-z]+/(NN|NNS) [a-z]+/(JJ|JJR|JJS) ");
    }

    @Override
    public String opinionPhrase(String[] taggedWords) {
        return taggedWords[1];
    }

    @Override
    public int featurePosition() {
        return 0;
    }
}
