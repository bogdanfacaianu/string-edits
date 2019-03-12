package com.string.edits.persistence.repository;

import com.string.edits.domain.Language;

public interface LanguageRepository {

    void save(Language language);

    void addWordToLanguage(String languageName, String word);

    void delete(String languageName);

    Language findLanguage(String name);

    void removeWordFromLanguage(String languageName, String word);

}