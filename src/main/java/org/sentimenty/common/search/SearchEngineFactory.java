package org.sentimenty.common.search;

/**
 * Created by serkan on 19.05.2015.
 */
public final class SearchEngineFactory {

    public static SearchEngine get(String id) {
        if (id.equals("Google")) {
            return new GoogleSearcher();
        } else {
            throw new IllegalArgumentException("No search engine for this id");
        }
    }

}
