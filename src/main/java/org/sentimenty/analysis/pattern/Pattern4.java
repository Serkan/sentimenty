package org.sentimenty.analysis.pattern;

import java.util.regex.Pattern;

/**
 * Created by serkan on 19.05.2015.
 */
public class Pattern4 extends PatternHandler {
    @Override
    public Pattern pattern() {
        return Pattern.compile("[a-z]+/(NN|NNS) [a-z]+/(RB|RBR|RBS) [a-z]+/(JJ|JJR|JJS) ");
    }

    @Override
    public String opinionPhrase(String[] taggedWords) {
        return taggedWords[1] + taggedWords[2];
    }

    @Override
    public int featurePosition() {
        return 0;
    }
}
