package com.stringedits.testrunner.controller.rest;

import com.github.liblevenshtein.transducer.Algorithm;
import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping(value = "/getMatches", produces = "application/json")
    public String getMatchesWithMaxDistance(
        @RequestParam("languageName") String languageName,
        @RequestParam("word") String word,
        @RequestParam(value = "algorithm", defaultValue = "STANDARD") Algorithm algorithm,
        @RequestParam(value = "maxDistance", defaultValue = "5") int maxDistance) {

        SearchDTO searchDTO = new SearchDTO(languageName, word, algorithm, maxDistance);
        TermQuery resultsForWord = dictionaryService.getResultsForWord(searchDTO);
        return dictionaryService.convertToJsonOutput(resultsForWord);
    }

    @GetMapping(value = "/getMatches/{languageName}/{word}", produces = "application/json")
    public String getMatches(
        @PathVariable("languageName") String languageName,
        @PathVariable("word") String word,
        @RequestParam(value = "algorithm", defaultValue = "STANDARD") Algorithm algorithm,
        @RequestParam(value = "maxDistance", defaultValue = "5") int maxDistance) {

        SearchDTO searchDTO = new SearchDTO(languageName, word, algorithm, maxDistance);
        TermQuery resultsForWord = dictionaryService.getResultsForWord(searchDTO);
        return dictionaryService.convertToJsonOutput(resultsForWord);
    }
}
