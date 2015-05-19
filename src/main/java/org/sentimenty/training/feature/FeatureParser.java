package org.sentimenty.training.feature;

import org.sentimenty.common.data.OpinionPhrase;
import org.sentimenty.common.data.Review;
import org.sentimenty.common.util.ReviewFileUtil;
import org.sentimenty.common.config.SentimentyConfig;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by serkan on 17.05.2015.
 */
public class FeatureParser {

    public List<OpinionPhrase> findFeatures(String category) throws IOException {
        Path reviewPath = Paths.get(SentimentyConfig.TAGGED_DIR + category);
        List<OpinionPhrase> allOpinionPhrases = new ArrayList<>();
        Files.walkFileTree(reviewPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!Files.isDirectory(file)) {
                    List<Review> reviews = ReviewFileUtil.readFile(file.toString());
                    for (Review review : reviews) {
                        List<OpinionPhrase> opinionPhrase = findOpinionPhrase(review.getBody());
                        allOpinionPhrases.addAll(opinionPhrase);
                    }
                }
                return super.visitFile(file, attrs);
            }
        });
        return allOpinionPhrases;
    }


    public List<OpinionPhrase> findOpinionPhrase(String reviewBody) {
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.compile("[a-z]+/(NN|NNS) [a-z]+/(VBP|VBZ) [a-z]+/(JJ|JJR|JJS) "));

        patterns.add(Pattern.compile("[a-z]+/(NN|NNS) [a-z]+/(VBP|VBZ) [a-z]+/(RB) [a-z]+/(JJ|JJR|JJS) "));

        patterns.add(Pattern.compile("[a-z]+/(NN|NNS) [a-z]+/(JJ|JJR|JJS) "));

        patterns.add(Pattern.compile("[a-z]+/(NN|NNS) [a-z]+/(RB|RBR|RBS) [a-z]+/(JJ|JJR|JJS) "));

        patterns.add(Pattern.compile("[a-z]+/(JJ|JJR|JJS) [a-z]+/(NN|NNS) "));

        patterns.add(Pattern.compile("[a-z]+/(RB|RBR|RBS) [a-z]+/(JJ|JJR|JJS) [a-z]+/(NN|NNS)"));

//        patterns.add(Pattern.compile("[a-z]+/(JJ|JJR|JJS) [a-z]+/(NN|NNS) [a-z]+/(NN|NNS)"));
        patterns.add(Pattern.compile("[a-z]+/(RB|RBR|RBS) [a-z]+/(JJ|JJR|JJS) [a-z]+/(NN|NNS) "));

//        patterns.add(Pattern.compile("[a-z]+/(RB|RBR|RBS) [a-z]+/(JJ|JJR|JJS) "));
//        patterns.add(Pattern.compile("[a-z]+/(RB|RBR|RBS) [a-z]+/(RB|RBR|RBS) [a-z]+/(JJ|JJR|JJS) "));

        Pattern nounPattern = Pattern.compile("[a-z]+/(NN|NNS)");
        return patterns.stream().map(p -> {
            Matcher matcher = p.matcher(reviewBody);
            List<OpinionPhrase> groups = new LinkedList<>();
            while (matcher.find()) {

                String opinonPhrase = matcher.group();
                Matcher nounMathcer = nounPattern.matcher(opinonPhrase);
                nounMathcer.find();
                String noun = nounMathcer.group().split("/")[0];
                groups.add(new OpinionPhrase(noun, opinonPhrase));
            }
            return groups;
        }).flatMap(l -> l.stream())
                .collect(Collectors.toList());
    }
}
