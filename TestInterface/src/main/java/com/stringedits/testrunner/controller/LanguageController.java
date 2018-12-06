package com.stringedits.testrunner.controller;

import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryOperations;
import com.string.edits.service.DictionaryService;
import com.thehutgroup.fusion.core.services.HealthcheckService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

    private final HealthcheckService healthcheckService;
    private final DictionaryService dictionaryService;
    private final DictionaryOperations dictionaryOperations;

    @Autowired
    public LanguageController(HealthcheckService healthcheckService,
        DictionaryService dictionaryService,
        DictionaryOperations dictionaryOperations) {
        this.healthcheckService = healthcheckService;
        this.dictionaryService = dictionaryService;
        this.dictionaryOperations = dictionaryOperations;
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
        Optional<Language> languageOptional = dictionaryService.findLanguageByName(languageName);
        if (languageOptional.isPresent()) {
            Optional<ITransducer<Candidate>> optionalCandidateITransducer =
                dictionaryOperations.buildTransducerForLanguage(languageOptional.get());

            if (optionalCandidateITransducer.isPresent()) {
                ITransducer<Candidate> transducer = optionalCandidateITransducer.get();
                TermQuery termQuery = dictionaryOperations.returnResults(transducer, word);
                return termQuery;
            }
        }
        throw new RuntimeException();
    }

    @RequestMapping("/listLanguage/{languageName}")
    public Language getLanguage(@PathVariable("languageName") String languageName) {
        Optional<Language> languageOptional = dictionaryService.findLanguageByName(languageName);
        if (languageOptional.isPresent()) {
            return languageOptional.get();
        }
        throw new RuntimeException();
    }
}