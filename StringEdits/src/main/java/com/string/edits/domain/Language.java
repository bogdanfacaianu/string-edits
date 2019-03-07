package com.string.edits.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Language {

    private String name;
    private Map<String, String> dictionary = new HashMap<>();

    public Language(String name) {
        this.name = name;
    }

    public Language(String name, Map<String, String> dictionary) {
        this.name = name;
        this.dictionary = dictionary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public void addWord(String word, String description) {
        dictionary.put(word, description);
    }

    public String removeWord(String word) {
        return dictionary.remove(word);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Language)) {
            return false;
        }
        Language language = (Language) o;
        return Objects.equals(name, language.name) &&
            Objects.equals(dictionary, language.dictionary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dictionary);
    }
}
