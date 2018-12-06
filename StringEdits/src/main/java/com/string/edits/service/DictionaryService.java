package com.string.edits.service;

import com.string.edits.domain.Language;
import com.string.edits.persistence.repository.LanguageRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class DictionaryService {

    @Autowired
    private LanguageRepository languageRepository;

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
