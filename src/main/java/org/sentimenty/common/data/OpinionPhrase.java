package org.sentimenty.common.data;

/**
 * Created by serkan on 18.05.2015.
 */
public class OpinionPhrase {

    private String noun;

    private String phrase;

    public OpinionPhrase(String noun, String phrase) {
        this.noun = noun;
        this.phrase = phrase;
    }

    public String getNoun() {
        return noun;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public String toString() {
        return "OpinionPhrase{" +
                "noun='" + noun + '\'' +
                ", phrase='" + phrase + '\'' +
                '}';
    }
}
