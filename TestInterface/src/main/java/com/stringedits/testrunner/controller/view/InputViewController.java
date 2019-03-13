package com.stringedits.testrunner.controller.view;

import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/input")
public class InputViewController {

    private final DictionaryService dictionaryService;

    @Autowired
    public InputViewController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping(value = "/getMatches", produces = "application/json")
    public String getMatchesWithMaxDistance(
        @RequestParam("languageName") String languageName,
        @RequestParam("word") String word,
        @RequestParam(value = "maxDistance", defaultValue = "5") int maxDistance,
        RedirectAttributes redirect) {

        TermQuery resultsForWord = dictionaryService.getResultsForWord(languageName, word, maxDistance);
        redirect.addFlashAttribute("language", languageName);
        redirect.addFlashAttribute("result", resultsForWord);
        redirect.addFlashAttribute("term", resultsForWord.getTerm());
        redirect.addFlashAttribute("distancesToWord", resultsForWord.getMatches());

        return "redirect:/output";
    }
}
