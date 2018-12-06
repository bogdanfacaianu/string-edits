package com.string.edits.persistence.repository;

import com.string.edits.domain.Language;

public interface LanguageRepository {

    void save(Language language);

    void addPatternToLanguage(String languageName, String pattern);

    void delete(String languageName);

    Language findLanguage(String name);

}