package com.string.edits.service;

import com.google.gson.Gson;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import com.string.edits.persistence.repository.LanguageRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class DictionaryService {

    private final LanguageRepository languageRepository;
    private final DictionaryOperations dictionaryOperations;
    private final Gson gson;

    @Autowired
    public DictionaryService(LanguageRepository languageRepository, DictionaryOperations dictionaryOperations, Gson gson) {
        this.languageRepository = languageRepository;
        this.dictionaryOperations = dictionaryOperations;
        this.gson = gson;
    }

    public Optional<Language> findLanguageByName(String languageName) {
        return Optional.ofNullable(languageRepository.findLanguage(languageName));
    }

    public void saveLanguage(Language language) {
        languageRepository.save(language);
    }

    public void addPatternToLanguage(String languageName, String word) {
        languageRepository.addPatternToLanguage(languageName, word);
    }

    public TermQuery getResultsForWord(String languageName, String word, int maxDistance) {
        TermQuery termQuery = new TermQuery(word);
        Optional<Language> languageOptional = findLanguageByName(languageName);
        if (languageOptional.isPresent()) {
            termQuery = dictionaryOperations.returnResults(languageOptional.get(), word, maxDistance);
        }
        return termQuery;
    }

    public void removeLanguage(String languageName) {
        languageRepository.delete(languageName);
    }

    public String convertToJsonOutput(TermQuery termQuery) {
        return gson.toJson(termQuery);
    }
}
