package com.stringedits.testrunner.controller.view;

import com.string.edits.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    private final DictionaryService dictionaryService;

    @Autowired
    public ViewController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/load")
    public String loadView() {
        return "load";
    }

    @GetMapping("/input")
    public String inputView(Model model) {
        model.addAttribute("languages", dictionaryService.findAllLanguages());
        return "input";
    }

    @GetMapping("/output")
    public String outputView() {
        return "output";
    }

    @GetMapping("/bulkAnalysis")
    public String bulkAnalysisView(Model model) {
        model.addAttribute("languages", dictionaryService.findAllLanguages());
        return "bulkAnalysis";
    }

    @GetMapping("/bulkOutput")
    public String bulkOutputView() {
        return "bulkOutput";
    }
}
