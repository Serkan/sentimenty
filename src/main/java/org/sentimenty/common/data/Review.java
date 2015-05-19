package org.sentimenty.common.data;

/**
 * Created by serkan on 02.05.2015.
 */
public class Review {

    private String owner;

    private String date;

    private String rating;

    private String body;

    public Review(String owner, String date, String rating, String body) {
        this.owner = owner;
        this.date = date;
        this.rating = rating;
        this.body = body;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
