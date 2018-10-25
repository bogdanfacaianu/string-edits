package com.string.edits.domain;

import java.util.List;
import java.util.Objects;

public class TermQuery {

    private String term;
    private List<DistanceToWord> matches;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<DistanceToWord> getMatches() {
        return matches;
    }

    public void setMatches(List<DistanceToWord> matches) {
        this.matches = matches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TermQuery termQuery = (TermQuery) o;
        return Objects.equals(term, termQuery.term) &&
                Objects.equals(matches, termQuery.matches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, matches);
    }

    @Override
    public String toString() {
        return "TermQuery{" +
                "term='" + term + '\'' +
                ", matches=" + matches +
                '}';
    }
}
