package org.sentimenty.training.feature;

import edu.stanford.nlp.util.Pair;
import org.sentimenty.common.util.ReviewFileUtil;
import org.sentimenty.common.config.SentimentyConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by serkan on 17.05.2015.
 */
public class FrequentNounStrategy {

    public List<String> findFeatures(String category) {
        Path reviewPath = Paths.get(SentimentyConfig.TAGGED_DIR + category);
        try {
            return Files.walk(reviewPath).filter(p -> !Files.isDirectory(p))
                    .flatMap(p -> ReviewFileUtil.readFile(p.toString()).stream())
                            // convert Stream<Review> to Map<Word, Count>
                    .map(r -> {
                        String body = r.getBody();
//                        Pattern p = Pattern.compile("[a-z]+/(NN|NNS)");
                        Pattern p = Pattern.compile("[a-z]+/(NN|NNS) [a-z]+/(VBP|VBZ) [a-z]+/(JJ) ");
                        Matcher matcher = p.matcher(body);
                        List<Pair<String, Integer>> nouns = new LinkedList<>();
                        while (matcher.find()) {
                            String group = matcher.group();
                            String[] split = group.split("/");
                            String word = split[0];
                            nouns.add(new Pair<>(word, 1));
                        }
                        return nouns;
                    })
                    .flatMap(l -> l.stream())
                    .collect(Collectors.groupingBy(p -> p.first(),
                            Collectors.mapping(p -> p.second(), Collectors.counting())))
                    .entrySet()
                    .stream()
                    .sorted((o1, o2) -> {
                        if (o1.getValue() > o2.getValue()) {
                            return -1;
                        } else if (o1.getValue() < o2.getValue()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }).map(e -> e.getKey())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
