package com.string.edits.service;

import com.string.edits.domain.Language;
import com.string.edits.persistence.repository.LanguageRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryService {

    private final LanguageRepository languageRepository;

    @Autowired
    public DictionaryService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Optional<Language> findLanguageByName(String languageName) {
        return Optional.ofNullable(languageRepository.findLanguage(languageName));
    }

    public Set<String> getDictionaryEntries(String languageName) {
        Optional<Language> languageOpt = findLanguageByName(languageName);
        if (languageOpt.isPresent()) {
            return languageOpt.get().getDictionary();
        }
        return Collections.emptySet();
    }

    public void saveLanguage(Language language) {
        languageRepository.save(language);
    }

}
