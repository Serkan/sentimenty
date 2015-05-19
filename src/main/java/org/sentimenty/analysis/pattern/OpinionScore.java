package org.sentimenty.analysis.pattern;

/**
 * Created by serkan on 19.05.2015.
 */
public class OpinionScore {

    private String feature;

    private String opinionPhrase;

    private double score;

    public OpinionScore(String feature, String opinionPhrase, double score) {
        this.feature = feature;
        this.opinionPhrase = opinionPhrase;
        this.score = score;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getOpinionPhrase() {
        return opinionPhrase;
    }

    public void setOpinionPhrase(String opinionPhrase) {
        this.opinionPhrase = opinionPhrase;
    }

    public double getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "OpinionScore{" +
                "feature='" + feature + '\'' +
                ", opinionPhrase='" + opinionPhrase + '\'' +
                ", score=" + score +
                '}';
    }
}
