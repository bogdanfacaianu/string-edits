package com.stringedits.testrunner.controller;

import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryService;
import com.thehutgroup.fusion.core.services.HealthcheckService;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

    private final HealthcheckService healthcheckService;
    private final DictionaryService dictionaryService;

    @Autowired
    public LanguageController(HealthcheckService healthcheckService,
        DictionaryService dictionaryService) {
        this.healthcheckService = healthcheckService;
        this.dictionaryService = dictionaryService;
    }

    @RequestMapping("/healthcheck")
    public String healthcheck() {
        return healthcheckService.createHealthcheckStatusMessage().getMessage();
    }

    @RequestMapping("/add/{languageName}/{word}")
    public String add(@PathVariable("languageName") String languageName, @PathVariable("word") String word) {
        dictionaryService.addPatternToLanguage(languageName, word);
        return "OK: " + word + " added in " + languageName;
    }

    @RequestMapping("/createLanguage/{languageName}")
    public String createLanguage(@PathVariable("languageName") String languageName) {
        dictionaryService.saveLanguage(new Language(languageName));
        return "OK: " + languageName + " created";
    }

    @RequestMapping("/getMatches/{languageName}/{word}")
    public TermQuery getMatches(@PathVariable("languageName") String languageName, @PathVariable("word") String word) {
        return dictionaryService.getResultsForWord(languageName, word);
    }

    @RequestMapping("/listLanguage/{languageName}")
    public Language getLanguage(@PathVariable("languageName") String languageName) {
        Optional<Language> languageOptional = dictionaryService.findLanguageByName(languageName);
        if (languageOptional.isPresent()) {
            return languageOptional.get();
        }
        return new Language(null);
    }

    @RequestMapping("/loadEnglishWords")
    public Language loadEnglishWordsFromFile() throws FileNotFoundException {
        String path = getClass().getClassLoader().getResource("dictionary/words.txt").getPath();
        Scanner file = new Scanner(new File(path));

        Set<String> words = new HashSet<>();
        while (file.hasNext()) {
            words.add(file.next().toLowerCase());
        }

        Language language = new Language("english", words);
        dictionaryService.saveLanguage(language);
        return language;
    }

    @RequestMapping("/removeLanguage/{languageName}")
    public String removeLanguage(@PathVariable("languageName") String languageName) {
        dictionaryService.removeLanguage(languageName);
        return "Removed: " + languageName;
    }
}