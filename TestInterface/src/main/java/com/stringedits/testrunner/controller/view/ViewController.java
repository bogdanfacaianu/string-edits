package com.stringedits.testrunner.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/load")
    public String loadView() {
        return "load";
    }

    @GetMapping("/input")
    public String inputView() {
        return "input";
    }

    @GetMapping("/output")
    public String outputView() {
        return "output";
    }

    @GetMapping("/bulkAnalysis")
    public String bulkAnalysisView() {
        return "bulkAnalysis";
    }
}
