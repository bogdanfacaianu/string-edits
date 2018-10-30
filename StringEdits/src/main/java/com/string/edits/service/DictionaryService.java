package com.string.edits.service;

import com.string.edits.persistence.repository.LanguageRepository;

public class DictionaryService {

    private final LanguageRepository languageRepository;

    public DictionaryService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

}
