package com.string.edits.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Language {

    private String name;
    private List<String> patterns = new ArrayList<>();

    public Language(String name) {
        this.name = name;
    }

    public Language(String name, List<String> patterns) {
        this.name = name;
        this.patterns = patterns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }

    public void addPattern(String pattern) {
        patterns.add(pattern);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(name, language.name) &&
                Objects.equals(patterns, language.patterns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, patterns);
    }
}
