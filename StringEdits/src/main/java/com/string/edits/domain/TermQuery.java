package com.string.edits.domain;

import java.util.List;
import java.util.Objects;

public class TermQuery {

    private final String term;
    private String language;
    private List<DistanceToWord> matches;

    public TermQuery(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public List<DistanceToWord> getMatches() {
        return matches;
    }

    public void setMatches(List<DistanceToWord> matches) {
        this.matches = matches;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermQuery)) {
            return false;
        }
        TermQuery termQuery = (TermQuery) o;
        return Objects.equals(term, termQuery.term) &&
            Objects.equals(language, termQuery.language) &&
            Objects.equals(matches, termQuery.matches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, language, matches);
    }

    @Override
    public String toString() {
        return "TermQuery{" +
            "term='" + term + '\'' +
            ", language='" + language + '\'' +
            ", matches=" + matches +
            '}';
    }
}
