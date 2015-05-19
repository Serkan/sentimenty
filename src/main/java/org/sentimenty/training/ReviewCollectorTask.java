package org.sentimenty.training;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.sentimenty.common.config.SentimentyConfig;
import org.sentimenty.training.crawler.ReviewRequestBuilder;
import org.sentimenty.common.data.Review;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by serkan on 02.05.2015.
 */
public class ReviewCollectorTask implements Runnable {

    private String appId;

    private int firstPage;

    public ReviewCollectorTask(String appId) {
        this(appId, 1);
    }

    public ReviewCollectorTask(String appId, int firstPage) {
        this.appId = appId;
        this.firstPage = firstPage;
    }


    @Override
    public void run() {
        ReviewRequestBuilder requestBuilder = new ReviewRequestBuilder()
                .setAppId(appId);

        for (int i = firstPage; i <= 1000; i++) {
            System.out.println("Page Numger : " + i);
            ReviewRequestBuilder.ReviewRequest request = requestBuilder.setPageNumber(String.valueOf(i)).build();
            List<Review> reviews = request.doRequest();
            if (reviews.size() == 0) {
                break;
            }
            Gson gson = new Gson();
            String reviewListJson = gson.toJson(reviews);
            try {
                FileUtils.write(new File(SentimentyConfig.RAW_REVIEW_DIR + appId + "_" + i + ".json"), reviewListJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
