package org.sentimenty.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.sentimenty.common.data.Review;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by serkan on 16.05.2015.
 */
public final class ReviewFileUtil {

    private ReviewFileUtil() {
    }

    public static List<Review> readFile(String filePath) {
        String reviewDump = null;
        try {
            reviewDump = FileUtils.readFileToString(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Review>>() {

        }.getType();
        return gson.fromJson(reviewDump, type);
    }

}
