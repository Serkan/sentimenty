package org.sentimenty.common.search;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by serkan on 18.05.2015.
 */
public class GoogleSearcher implements SearchEngine {

    private static final String ADDRESS = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";

    private static final String CHARSET = "UTF-8";

    @Override
    public long hitCount(String query) {
        long hitCount = -1;
        try {
            URL url = new URL(ADDRESS + URLEncoder.encode(query, CHARSET));
            Reader reader = new InputStreamReader(url.openStream(), CHARSET);
            GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);

            GoogleResults.ResponseData responseData = results.getResponseData();

            if (results.getResponseStatus() == 200) {
                String resultCount = responseData.getCursor().getResultCount();
                resultCount = resultCount.replaceAll(",", "");
                hitCount = Long.valueOf(resultCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hitCount;
    }

    class GoogleResults {

        private ResponseData responseData;

        private String responseDetails;

        private Integer responseStatus;

        public String getResponseDetails() {
            return responseDetails;
        }

        public void setResponseDetails(String responseDetails) {
            this.responseDetails = responseDetails;
        }

        public Integer getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(Integer responseStatus) {
            this.responseStatus = responseStatus;
        }

        public ResponseData getResponseData() {
            return responseData;
        }

        public void setResponseData(ResponseData responseData) {
            this.responseData = responseData;
        }

        public String toString() {
            return "ResponseData[" + responseData + "]";
        }

        class ResponseData {

            private Cursor cursor;

            public Cursor getCursor() {
                return cursor;
            }

            public void setCursor(Cursor cursor) {
                this.cursor = cursor;
            }

        }

        class Cursor {
            private String resultCount;

            public String getResultCount() {
                return resultCount;
            }

            public void setResultCount(String resultCount) {
                this.resultCount = resultCount;
            }
        }

    }

}
