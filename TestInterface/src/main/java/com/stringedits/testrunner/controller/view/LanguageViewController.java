package com.stringedits.testrunner.controller.view;

import com.string.edits.domain.Language;
import com.string.edits.service.DictionaryService;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/load")
public class LanguageViewController {

    private final DictionaryService dictionaryService;

    @Autowired
    public LanguageViewController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping()
    public String load() {
        return "load";
    }

    @PostMapping("/loadEnglishWords")
    public String loadEnglishWordsFromFile() throws FileNotFoundException {
        return loadLanguage("english");
    }

    @PostMapping("/loadDictionary")
    public String loadEnglishWordsFromFile(@RequestParam("dictionaryName") String dictionaryName) throws FileNotFoundException {
        return loadLanguage(dictionaryName);
    }

    @PostMapping("/createLanguage")
    public String createLanguage(@RequestParam("languageName") String languageName) {
        dictionaryService.saveLanguage(new Language(languageName));
        return "redirect:/load";
    }

    @PostMapping("/addWordFromLanguage")
    public String addWord(
        @RequestParam("languageName") String languageName,
        @RequestParam("word") String word) {

        dictionaryService.addWordToLanguage(languageName, word);
        return "redirect:/load";
    }

    @GetMapping("/listLanguage")
    public String getLanguage(@RequestParam("languageName") String languageName) {
        return "redirect:/listLanguage/" + languageName;
    }

    @PostMapping("/removeWordFromLanguage")
    public String removeLanguage(
        @RequestParam("word") String word,
        @RequestParam("languageName") String languageName) {

        dictionaryService.removeWordFromLanguage(languageName, word);
        return "redirect:/load";
    }

    @PostMapping("/removeLanguage")
    public String removeLanguage(@RequestParam("languageName") String languageName) {
        dictionaryService.removeLanguage(languageName);
        return "redirect:/load";
    }

    private String loadLanguage(String name) throws FileNotFoundException {
        String path = Objects.requireNonNull(
            getClass().getClassLoader().getResource("dictionary/" + name + ".txt")).getPath();
        Scanner file = new Scanner(new File(path));

        Set<String> words = new HashSet<>();
        while (file.hasNext()) {
            words.add(file.next().toLowerCase());
        }

        Language language = new Language(name, words);
        dictionaryService.saveLanguage(language);
        return "redirect:/load";
    }
}
