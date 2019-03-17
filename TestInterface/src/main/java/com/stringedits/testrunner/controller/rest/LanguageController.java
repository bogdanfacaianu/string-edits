package com.stringedits.testrunner.controller.rest;

import com.string.edits.domain.Language;
import com.string.edits.service.DictionaryService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

    private final DictionaryService dictionaryService;

    @Autowired
    public LanguageController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @RequestMapping("/createLanguage/{languageName}")
    public String createLanguage(@PathVariable("languageName") String languageName) {
        dictionaryService.saveLanguage(new Language(languageName));
        return "OK: " + languageName + " created";
    }

    @RequestMapping("/add/{languageName}/{word}")
    public String addWord(
        @PathVariable("languageName") String languageName,
        @PathVariable("word") String word) {

        dictionaryService.addWordToLanguage(languageName, word);
        return "OK: " + word +" added in " + languageName ;
    }

    @RequestMapping("/listLanguage/{languageName}")
    public Language getLanguage(@PathVariable("languageName") String languageName) {
        Optional<Language> languageOptional = dictionaryService.findLanguageByName(languageName);
        return languageOptional.orElseGet(() -> new Language(null));
    }

    @RequestMapping("/remove/{word}/from/{languageName}")
    public String removeLanguage(
        @PathVariable("word") String word,
        @PathVariable("languageName") String languageName) {

        dictionaryService.removeWordFromLanguage(languageName, word);
        return "Removed: " + word;
    }

    @RequestMapping("/removeLanguage/{languageName}")
    public String removeLanguage(@PathVariable("languageName") String languageName) {
        dictionaryService.removeLanguage(languageName);
        return "Removed: " + languageName;
    }

    @RequestMapping("/clearCache")
    public String clearCache() {
        dictionaryService.clearCache();
        return "Cache Cleared";
    }
}