package org.sentimenty.training.crawler;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.sentimenty.common.data.Review;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by serkan on 01.05.2015.
 */
public class ReviewRequestBuilder {

    private static final String htmlStart = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head lang=\"en\">\n" +
            "    <meta charset=\"UTF-8\"/>\n" +
            "    <title></title>\n" +
            "</head>\n" +
            "<body>";
    private static final String htmlEnd = "</body>\n" +
            "</html>";

    private HttpPost post = new HttpPost("https://play.google.com/store/getreviews?authuser=0");

    private List<NameValuePair> form = new LinkedList<>();
    private String appId;
    private String pageNumber;

    public ReviewRequestBuilder() {
        post.setHeader("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        post.setHeader("origin", "https://play.google.com");
        post.setHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");

        form.add(new BasicNameValuePair("reviewType", "0"));
        form.add(new BasicNameValuePair("reviewSortOrder", "4"));
        form.add(new BasicNameValuePair("xhr", "1"));
        form.add(new BasicNameValuePair("hl", "en"));
    }

    public ReviewRequestBuilder setAppId(String appId) {
        this.appId = appId;
        post.setHeader("referer", "https://play.google.com/store/apps/details?id=" + appId + "&hl=en");
        form.add(new BasicNameValuePair("id", appId));
        return this;
    }

    public ReviewRequestBuilder setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
        form.add(new BasicNameValuePair("pageNum", pageNumber));
        return this;
    }

    public ReviewRequest build() {
        try {
            post.setEntity(new UrlEncodedFormEntity(form));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return new ReviewRequest(post, appId, pageNumber);
    }


    public class ReviewRequest {

        private HttpPost post;
        private final String appId;
        private final String pageNum;
        private CloseableHttpClient httpClient = HttpClients.createDefault();

        ReviewRequest(HttpPost post, String appId, String pageNum) {
            this.post = post;
            this.appId = appId;
            this.pageNum = pageNum;
        }

        public List<Review> doRequest() {
            List<Review> reviews = new LinkedList<>();
            try {
                CloseableHttpResponse response = httpClient.execute(post);

                if (response.getStatusLine().getStatusCode() == 200) {
                    InputStream is = response.getEntity().getContent();
                    String s = IOUtils.toString(is);
                    if (s.startsWith(")]}'")) {
                        s = s.substring(3, s.length()).trim();
                        s = s.substring(1, s.length());
                    }

                    Gson gson = new Gson();
                    ArrayList arrayList = gson.fromJson(s, ArrayList.class);
                    ArrayList o = (ArrayList) arrayList.get(0);
                    if (o.size() > 2) {
                        String comments = o.toString();
                        comments = comments.substring(11, comments.length() - 5).trim();
                        comments = htmlStart + comments + htmlEnd;

                        InputStream is1 = IOUtils.toInputStream(comments);
                        TagNode clean = new HtmlCleaner().clean(is1);
                        Document doc = new DomSerializer(
                                new CleanerProperties()).createDOM(clean);

                        XPathFactory xPathfactory = XPathFactory.newInstance();
                        XPath xpath = xPathfactory.newXPath();
                        XPathExpression countExp = xpath.compile("count(/html/body/div)");

                        Integer count = Integer.valueOf(countExp.evaluate(doc));

                        for (int i = 0; i < count; i++) {
                            XPathExpression reviewer = xpath.compile("/html/body/div[@class='single-review'][" + i + "]/div[@class='review-header']/div[@class='review-info']/span[@class='author-name']/a");
                            XPathExpression reviewDatexp = xpath.compile("/html/body/div[@class='single-review'][" + i + "]/div[@class='review-header']/div[@class='review-info']/span[@class='review-date']");
                            XPathExpression reviewBodyExp = xpath.compile("/html/body/div[@class='single-review'][" + i + "]/div[@class='review-body']");
                            XPathExpression rateExp = xpath.compile("/html/body/div[@class='single-review'][" + i + "]/div[@class='review-header']/div[@class='review-info']/div[@class='review-info-star-rating']/div[@class='tiny-star star-rating-non-editable-container']/div[@class='current-rating']/@style");

                            String name = reviewer.evaluate(doc);
                            if (name.isEmpty()) {
                                name = "anonymous";
                            }
                            String date = reviewDatexp.evaluate(doc);
                            String reviewBody = reviewBodyExp.evaluate(doc);

                            String rate = rateExp.evaluate(doc);
                            Pattern ratePattern = Pattern.compile("[0-9]+");
                            Matcher matcher = ratePattern.matcher(rate);
                            String stars = "0";
                            if (matcher.find()) {
                                stars = matcher.group();
                            }

                            Review review = new Review(name, date, stars, reviewBody);
                            reviews.add(review);
                        }
                        return reviews;
                    }
                } else {
                    System.err.println("Wrong status code for " + appId + " , page number " + pageNum
                            + " : " + response.getStatusLine().getStatusCode());
                }
            } catch (Exception e) {
                System.err.println("Exception for " + appId + " , page number " + pageNum);
                e.printStackTrace();
            }
            return reviews;
        }

    }
}
