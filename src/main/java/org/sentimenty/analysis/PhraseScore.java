package org.sentimenty.analysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by serkan on 18.05.2015.
 */
public class PhraseScore {

    private List<String> adjectives;

    private List<String> adverbs;

    private String feature;

    private int opnionOrientation;

    public PhraseScore(String feature) {
        this.feature = feature;
        adjectives = new ArrayList<>();
        adverbs = new ArrayList<>();
    }

    public void addAdj(String word) {
        adjectives.add(word);
    }

    public void addAdv(String word) {
        adverbs.add(word);
    }

    public int getOpnionOrientation() {
        return opnionOrientation;
    }

    public void setOpnionOrientation(int opnionOrientation) {
        this.opnionOrientation = opnionOrientation;
    }

    public List<String> getAdjectives() {
        return adjectives;
    }

    public List<String> getAdverbs() {
        return adverbs;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
