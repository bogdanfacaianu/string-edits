package com.stringedits.testrunner.controller;

import com.string.edits.service.DictionaryService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    private final DictionaryService dictionaryService;

    @Autowired
    public ViewController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @RequestMapping("/welcome")
    public String loginMessage(){
        return "welcome";
    }


    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Ömerrrr");
        model.addAttribute("mode", "dev");

        return "index";
    }

    @RequestMapping("/input")
    public String inputView(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "bob");
        model.addAttribute("mode", "dev");
        return "input";
    }

    @PostMapping("/processTextAnalysis")
    public String processTextAnalysis(
        @RequestParam("language") String language,
        @RequestParam("term") String term,
        Model model) {
        dictionaryService.getResultsForWord(language, term, 5);
        model.addAttribute("mode", "dev");
        return "output";
    }
}
