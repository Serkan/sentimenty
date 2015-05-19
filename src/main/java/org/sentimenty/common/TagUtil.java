package org.sentimenty.common;

/**
 * Created by serkan on 19.05.2015.
 */
public class TagUtil {

    public static String getTag(String taggedWord) {
        return taggedWord.split("/")[1];
    }

    public static String stripTag(String taggedWord) {
        return taggedWord.split("/")[0];
    }
}
