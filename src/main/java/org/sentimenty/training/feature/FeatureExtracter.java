package org.sentimenty.training.feature;

import com.google.gson.Gson;
import edu.stanford.nlp.util.Pair;
import org.apache.commons.io.FileUtils;
import org.sentimenty.common.util.PMIIRScoreCalculator;
import org.sentimenty.common.config.SentimentyConfig;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by serkan on 17.05.2015.
 */
public class FeatureExtracter {

    private String category;

    public FeatureExtracter(String category) {
        this.category = category;
    }

    public void extract() throws IOException {
        FeatureParser parser = new FeatureParser();
        List<Pair<Map.Entry<String, Long>, Double>> possibleFeatures = parser.findFeatures(category)
                .stream().collect(Collectors.groupingBy(p -> p.getNoun(),
                        Collectors.mapping(p -> p.getNoun(), Collectors.counting())))
                .entrySet().stream()
                .map(e -> new Pair<>(e, PMIIRScoreCalculator.calculate(e.getKey(), category))).sorted((o1, o2) -> {
                    if (o1.second() > o2.second()) {
                        return 1;
                    } else if (o1.second() < o2.second()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }).collect(Collectors.toList());
        Gson gson = new Gson();
        String json = gson.toJson(possibleFeatures);
        FileUtils.write(new File(SentimentyConfig.FEATURES_DIR + category + "features"), json);
    }
}
