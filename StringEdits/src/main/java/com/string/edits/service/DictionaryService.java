package com.string.edits.service;

import com.google.gson.Gson;
import com.string.edits.domain.Language;
import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import com.string.edits.persistence.repository.LanguageRepository;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryService {

    private static final Logger LOG = LoggerFactory.getLogger(DictionaryService.class);

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

    public void addWordToLanguage(String languageName, String word) {
        languageRepository.addWordToLanguage(languageName, word);
    }

    public void removeLanguage(String languageName) {
        languageRepository.delete(languageName);
    }

    public String convertToJsonOutput(TermQuery termQuery) {
        return gson.toJson(termQuery);
    }

    public void removeWordFromLanguage(String languageName, String word) {
        languageRepository.removeWordFromLanguage(languageName, word);
    }

    public List<String> findAllLanguages() {
        return languageRepository.findAllLanguages();
    }

    public TermQuery getResultsForWord(SearchDTO searchDTO) {
        TermQuery termQuery = new TermQuery(searchDTO.getSearchTerm(), searchDTO.getLanguage());
        Optional<Language> languageOptional = findLanguageByName(searchDTO.getLanguage());
        if (!languageOptional.isPresent()) {
            LOG.error("No language found in bucket for {}", searchDTO.getLanguage());
            return termQuery;
        }
        return dictionaryOperations.returnResults(languageOptional.get(), searchDTO);
    }

    public Map<String, TermQuery> getResultsForLanguages(List<String> languages, SearchDTO searchDTO) {
        Map<String, TermQuery> results = new HashMap<>();
        for (String language : languages) {
            searchDTO.setLanguage(language);
            results.put(language, getResultsForWord(searchDTO));
        }
        return results;
    }

    public Map<String, Collection<TermQuery>> getResultsForWordsInLanguages(List<String> languages, List<String> words, SearchDTO searchDTO) {
        Map<String, Collection<TermQuery>> wordResults = new HashMap<>();
        for (String word : words) {
            searchDTO.setSearchTerm(word);
            Collection<TermQuery> results = getResultsForLanguages(languages, searchDTO).values();
            wordResults.put(word, results);
        }
        return wordResults;
    }

    public void clearCache() {
        dictionaryOperations.clearCache();
    }
}