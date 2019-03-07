package com.stringedits.testrunner.controller;

import com.string.edits.service.DictionaryService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

//    @RequestMapping("sendTextForAnalysis")
}
