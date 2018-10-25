package com.string.edits.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Language {

    private String name;
    private List<String> dictionary = new ArrayList<>();

    public Language(String name) {
        this.name = name;
    }

    public Language(String name, List<String> dictionary) {
        this.name = name;
        this.dictionary = dictionary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<String> dictionary) {
        this.dictionary = dictionary;
    }

    public void addPattern(String pattern) {
        dictionary.add(pattern);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(name, language.name) &&
                Objects.equals(dictionary, language.dictionary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dictionary);
    }
}
