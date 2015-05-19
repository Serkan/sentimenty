package org.sentimenty.analysis.pattern;

import org.sentimenty.common.TagUtil;

import java.util.regex.Pattern;

/**
 * Created by serkan on 19.05.2015.
 */
public class Pattern1 extends PatternHandler {


    @Override
    public Pattern pattern() {
        return Pattern.compile("[a-z]+/(NN|NNS) [a-z]+/(VBP|VBZ) [a-z]+/(JJ|JJR|JJS) ");
    }

    @Override
    public String opinionPhrase(String[] taggedWords) {
        return TagUtil.stripTag(taggedWords[2]);
    }


    @Override
    public int featurePosition() {
        return 0;
    }

}
