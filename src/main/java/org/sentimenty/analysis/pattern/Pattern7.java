package org.sentimenty.analysis.pattern;

import java.util.regex.Pattern;

/**
 * Created by serkan on 19.05.2015.
 */
public class Pattern7 extends PatternHandler {
    @Override
    public Pattern pattern() {
        return Pattern.compile("[a-z]+/(RB|RBR|RBS) [a-z]+/(JJ|JJR|JJS) [a-z]+/(NN|NNS) ");
    }

    @Override
    public String opinionPhrase(String[] taggedWords) {
        return taggedWords[0] + taggedWords[1];
    }

    @Override
    public int featurePosition() {
        return 2;
    }
}
