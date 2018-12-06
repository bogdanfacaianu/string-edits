package com.string.edits.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Language {

    private String name;
    private Set<String> dictionary = new HashSet<>();

    public Language(String name) {
        this.name = name;
    }

    public Language(String name, Set<String> dictionary) {
        this.name = name;
        this.dictionary = dictionary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Set<String> dictionary) {
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
