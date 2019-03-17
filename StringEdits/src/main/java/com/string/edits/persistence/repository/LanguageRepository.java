package com.string.edits.persistence.repository;

import com.string.edits.domain.Language;
import java.util.List;

public interface LanguageRepository {

    void save(Language language);

    void addWordToLanguage(String languageName, String word);

    void delete(String languageName);

    Language findLanguage(String name);

    void removeWordFromLanguage(String languageName, String word);

    List<String> findAllLanguages();

}