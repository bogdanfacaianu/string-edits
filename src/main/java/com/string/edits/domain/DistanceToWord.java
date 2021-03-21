package com.string.edits.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DistanceToWord {

    private final String word;
    private int distance;
    private List<WordEdits> edits = new ArrayList<>();

    public DistanceToWord(String word) {
        this.word = word;
        this.distance = 0;
    }

    public DistanceToWord(String word, int distance) {
        this.word = word;
        this.distance = distance;
    }

    public String getWord() {
        return word;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<WordEdits> getEdits() {
        return edits;
    }

    public void setEdits(List<WordEdits> edits) {
        this.edits = edits;
    }

    public boolean equalDistanceTo(int givenDistance) {
        return this.distance == givenDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistanceToWord that = (DistanceToWord) o;
        return distance == that.distance &&
                Objects.equals(word, that.word) &&
                Objects.equals(edits, that.edits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, distance, edits);
    }

    @Override
    public String toString() {
        return "DistanceToWord{" +
                "word='" + word + '\'' +
                ", distance=" + distance +
                ", edits=" + edits +
                '}';
    }
}
