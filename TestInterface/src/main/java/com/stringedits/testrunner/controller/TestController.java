package com.stringedits.testrunner.controller;

import com.string.edits.domain.Language;
import com.string.edits.service.DictionaryService;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final DictionaryService dictionaryService;

    @Autowired
    public TestController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @RequestMapping("/loadEnglishWords")
    public Language loadEnglishWordsFromFile() throws FileNotFoundException {
        String path = Objects.requireNonNull(
            getClass().getClassLoader().getResource("dictionary/words.txt")).getPath();
        Scanner file = new Scanner(new File(path));

        Map<String, String> words = new HashMap<>();
        while (file.hasNext()) {
            words.put(file.next().toLowerCase(), null);
        }

        Language language = new Language("english", words);
        dictionaryService.saveLanguage(language);
        return language;
    }
}
