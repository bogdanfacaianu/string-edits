package com.stringedits.testrunner.controller.rest;

import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping(value = "/getMatches/{languageName}/{word}", produces = "application/json")
    public String getMatches(
        @PathVariable("languageName") String languageName,
        @PathVariable("word") String word) {

        TermQuery resultsForWord = dictionaryService.getResultsForWord(languageName, word, 5);
        return dictionaryService.convertToJsonOutput(resultsForWord);
    }

    @PostMapping(value = "/getMatches/{languageName}/{word}/withMaxDistance/{maxDistance}", produces = "application/json")
    public String getMatchesWithMaxDistance(
        @PathVariable("languageName") String languageName,
        @PathVariable("word") String word,
        @PathVariable("maxDistance") int maxDistance) {

        TermQuery resultsForWord = dictionaryService.getResultsForWord(languageName, word, maxDistance);
        return dictionaryService.convertToJsonOutput(resultsForWord);
    }
}
