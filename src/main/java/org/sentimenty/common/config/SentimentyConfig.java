package org.sentimenty.common.config;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by serkan on 17.05.2015.
 */
public final class SentimentyConfig {

    public static final String SENTIMENTY_BASE_DIR = "/home/serkan/dev/sentimenty_workspace_1/";

    public static final String RAW_REVIEW_DIR = SENTIMENTY_BASE_DIR + "/reviews/";

    public static final String FEATURES_DIR = SENTIMENTY_BASE_DIR + "/features/";

    public static final String TAGGED_DIR = SENTIMENTY_BASE_DIR + "/tagged/";

    public static final String POS_TAGGER_TRAINING_SET = SENTIMENTY_BASE_DIR + "/config/english-bidirectional-distsim.tagger";

    public static final String SEARCH_ENGINE_ID = "Google";


    static {
        createIfNotExist(SENTIMENTY_BASE_DIR);
        createIfNotExist(RAW_REVIEW_DIR);
        createIfNotExist(FEATURES_DIR);
        createIfNotExist(TAGGED_DIR);
    }

    private static void createIfNotExist(String file) {
        File baseDir = new File(file);
        if (!baseDir.exists()) {
            baseDir.mkdir();
        }
    }
}
