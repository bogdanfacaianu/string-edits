package com.string.edits.repository;

import com.string.edits.domain.Language;
import com.string.edits.persistence.repository.LanguageRepository;

import java.util.Map;

public class DefaultLanguageRepository implements LanguageRepository {

    private Map<String, Language> languageRepository;

    public DefaultLanguageRepository(Map<String, Language> languageRepository) {
        this.languageRepository = languageRepository;
    }

    public void save(Language language) {
        languageRepository.put(language.getName(), language);
    }

    public void addToLanguage(String languageName, String pattern) {
        Language languageEntity = languageRepository.get(languageName);
        languageEntity.addPattern(pattern);
        save(languageEntity);
    }

    public void findPatternInLanguage() {

    }

    public void delete(String languageName) {
        languageRepository.remove(languageName);
    }
}
