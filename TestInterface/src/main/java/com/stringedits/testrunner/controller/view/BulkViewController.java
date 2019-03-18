package com.stringedits.testrunner.controller.view;

import com.github.liblevenshtein.transducer.Algorithm;
import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bulkAnalysis")
public class BulkViewController {

    private final DictionaryService dictionaryService;

    @Autowired
    public BulkViewController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping(value = "/getBulkMatches", produces = "application/json")
    public String getBulkMatchesWithMaxDistance(
        @RequestParam("languages") String[] languages,
        @RequestParam("word") String text,
        @RequestParam(value = "algorithm", defaultValue = "STANDARD") Algorithm algorithm,
        @RequestParam(value = "maxDistance", defaultValue = "5") int maxDistance,
        RedirectAttributes redirect) {

        SearchDTO searchDTO = new SearchDTO(null, null, algorithm, maxDistance);
        List<String> words = Arrays.asList(text.split("\\s+"));
        List<Collection<TermQuery>> results = new ArrayList<>();
        for (String word : words) {
            searchDTO.setSearchTerm(word);
            Collection<TermQuery> resultsForWord = dictionaryService.getResultsForLanguages(Arrays.asList(languages), searchDTO).values();
            results.add(resultsForWord);
        }

        redirect.addFlashAttribute("languages", dictionaryService.findAllLanguages());
        redirect.addFlashAttribute("results", results);

        return "redirect:/bulkOutput";
    }
}
