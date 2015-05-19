package org.sentimenty.common;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.sentimenty.common.config.SentimentyConfig;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Created by serkan on 02.05.2015.
 */
public class POSUtil {

    public static String tag(String text) throws IOException {
        MaxentTagger tagger = new MaxentTagger(SentimentyConfig.POS_TAGGER_TRAINING_SET);
//
        StringBuilder builder = new StringBuilder();
        List<List<HasWord>> lists = MaxentTagger.tokenizeText(new StringReader(text));
        for (List<HasWord> list : lists) {
            List<TaggedWord> taggedWords = tagger.tagSentence(list);
            for (TaggedWord taggedWord : taggedWords) {
                builder.append(taggedWord.toString() + " ");
            }
        }
        return builder.toString();
    }

}
