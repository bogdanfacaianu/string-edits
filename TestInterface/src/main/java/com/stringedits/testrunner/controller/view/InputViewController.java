package com.stringedits.testrunner.controller.view;

import com.github.liblevenshtein.transducer.Algorithm;
import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryService;
import java.util.Arrays;
import java.util.Collection;
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
        @RequestParam("word") String word,
        @RequestParam(value = "algorithm", defaultValue = "STANDARD") Algorithm algorithm,
        @RequestParam(value = "maxDistance", defaultValue = "5") int maxDistance,
        @RequestParam("languages") String[] languages,
        RedirectAttributes redirect) {

        SearchDTO searchDTO = new SearchDTO(null, word, algorithm, maxDistance);
        Collection<TermQuery> resultsForWord = dictionaryService.getResultsForLanguages(Arrays.asList(languages), searchDTO).values();
        redirect.addFlashAttribute("languages", dictionaryService.findAllLanguages());
        redirect.addFlashAttribute("results", resultsForWord);
        redirect.addFlashAttribute("term", word);

        return "redirect:/output";
    }
}
