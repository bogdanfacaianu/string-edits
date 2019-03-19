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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/load")
public class LanguageViewController {

    private final DictionaryService dictionaryService;

    @Autowired
    public LanguageViewController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping("/loadEnglishWords")
    public String loadEnglishWordsFromFile(RedirectAttributes redirect) throws FileNotFoundException {
        return loadLanguage("english", redirect);
    }

    @PostMapping("/loadDictionary")
    public String loadEnglishWordsFromFile(
        @RequestParam("dictionaryName") String dictionaryName,
        RedirectAttributes redirect) throws FileNotFoundException {
        return loadLanguage(dictionaryName, redirect);
    }

    @PostMapping("/createLanguage")
    public String createLanguage(
        @RequestParam("languageName") String languageName,
        RedirectAttributes redirect) {

        dictionaryService.saveLanguage(new Language(languageName));
        redirect.addFlashAttribute("flashSuccess", String.format("Language \"%s\" created", languageName));
        return "redirect:/load";
    }

    @PostMapping("/addWordFromLanguage")
    public String addWord(
        @RequestParam("languageName") String languageName,
        @RequestParam("word") String word,
        RedirectAttributes redirect) {

        dictionaryService.addWordToLanguage(languageName, word);
        redirect.addFlashAttribute("flashSuccess", String.format("Added %s word in language \"%s\"", word, languageName));
        return "redirect:/load";
    }

    @PostMapping("/removeWordFromLanguage")
    public String removeLanguage(
        @RequestParam("word") String word,
        @RequestParam("languageName") String languageName,
        RedirectAttributes redirect) {

        dictionaryService.removeWordFromLanguage(languageName, word);
        redirect.addFlashAttribute("flashSuccess", String.format("Removed %s word from language \"%s\"", word, languageName));
        return "redirect:/load";
    }

    @PostMapping("/removeLanguage")
    public String removeLanguage(
        @RequestParam("languageName") String languageName,
        RedirectAttributes redirect) {

        dictionaryService.removeLanguage(languageName);
        redirect.addFlashAttribute("flashSuccess", String.format("Language \"%s\" removed", languageName));
        return "redirect:/load";
    }

    private String loadLanguage(String name, RedirectAttributes redirect) throws FileNotFoundException {
        String path = Objects.requireNonNull(
            getClass().getClassLoader().getResource("dictionary/" + name + ".txt")).getPath();
        Scanner file = new Scanner(new File(path));

        Set<String> words = new HashSet<>();
        while (file.hasNext()) {
            words.add(file.next().toLowerCase());
        }

        Language language = new Language(name, words);
        dictionaryService.saveLanguage(language);
        redirect.addFlashAttribute("flashSuccess", String.format("Language \"%s\" loaded", name));
        return "redirect:/load";
    }
}
