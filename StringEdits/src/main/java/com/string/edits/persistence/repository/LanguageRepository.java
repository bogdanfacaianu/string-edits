package com.string.edits.persistence.repository;

import com.string.edits.domain.Language;

public interface LanguageRepository {

    void save(Language language);

    void addToLanguage(String languageName, String pattern);

    void delete(String languageName);

}