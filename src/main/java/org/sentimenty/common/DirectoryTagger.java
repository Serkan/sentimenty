package org.sentimenty.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.apache.commons.io.FileUtils;
import org.sentimenty.common.data.Review;
import org.sentimenty.common.config.SentimentyConfig;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by serkan on 16.05.2015.
 */
public class DirectoryTagger {

    private static MaxentTagger tagger = new MaxentTagger(SentimentyConfig.POS_TAGGER_TRAINING_SET);

    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public void tagDirectory(String category) throws IOException {
        Path reviewPath = Paths.get(SentimentyConfig.RAW_REVIEW_DIR + category);

        Files.walkFileTree(reviewPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                Runnable task = () -> {
                    String fileName = path.toString();
                    String taggedFileName = SentimentyConfig.TAGGED_DIR + category + "/" + "tagged_" + path.getFileName();
                    File taggedFile = new File(taggedFileName);
                    if (!Files.isDirectory(path) &&
                            fileName.endsWith("json") &&
                            !fileName.startsWith("tagged") &&
                            !taggedFile.exists()) {
                        System.out.println("Tagging : " + path.toString());
                        String reviewDump = null;
                        try {
                            reviewDump = FileUtils.readFileToString(path.toFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Review>>() {

                        }.getType();
                        List<Review> reviewList = gson.fromJson(reviewDump, type);
                        List<Review> taggedList = tagReviewList(reviewList);
                        String taggedDump = gson.toJson(taggedList);

                        try {
                            FileUtils.write(taggedFile, taggedDump);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                };
                executor.submit(task);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static List<Review> tagReviewList(List<Review> reviewList) {
        List<Review> result = new ArrayList<>(reviewList.size());
        for (Review review : reviewList) {
            StringBuilder buffer = new StringBuilder();
            String body = review.getBody();
            body = body.replace("Full Review", "");
            List<List<HasWord>> lists = MaxentTagger.tokenizeText(new StringReader(body));
            for (List<HasWord> list : lists) {
                List<TaggedWord> taggedWords = tagger.tagSentence(list);
                for (TaggedWord taggedWord : taggedWords) {
                    buffer.append(taggedWord.word().toLowerCase() + "/" + taggedWord.tag().toUpperCase() + " ");
                }
            }
            String taggedBody = buffer.toString().trim();
            if (!taggedBody.endsWith("./.")) {
                taggedBody = taggedBody + "./.";
            }
            // copy to new review
            Review r = new Review(review.getOwner(), review.getDate(), review.getRating(), taggedBody);
            result.add(r);
        }
        return result;
    }

}
