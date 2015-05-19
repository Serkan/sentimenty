package org.sentimenty.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.stanford.nlp.util.Pair;
import org.apache.commons.io.FileUtils;
import org.sentimenty.common.config.SentimentyConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by serkan on 18.05.2015.
 */
public class FeatureFileUtil {

    public static List<String> selectFeatureSet(String category, double topNFeature) {
        String json = null;
        try {
            json = FileUtils.readFileToString(new File(SentimentyConfig.FEATURES_DIR + category + ".feature"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Type type = new TypeToken<List<Pair<Map.Entry<String, Long>, Double>>>() {

        }.getType();
        Gson gson = new Gson();
        List<Pair<Map.Entry<String, Long>, Double>> featureList = gson.fromJson(json, type);
        long top = Math.round(featureList.size() * topNFeature);
        return featureList.subList(0, (int) top).stream().map(p -> p.first().getKey()).collect(Collectors.toList());
    }

}
