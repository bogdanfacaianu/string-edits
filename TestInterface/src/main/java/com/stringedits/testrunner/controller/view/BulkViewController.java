package com.stringedits.testrunner.controller.view;

import com.github.liblevenshtein.transducer.Algorithm;
import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryService;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

        List<String> words = Arrays.asList(text.split("\\s+"));
        SearchDTO searchDTO = new SearchDTO(null, null, algorithm, maxDistance);
        Map<String, Collection<TermQuery>> wordResults =
            dictionaryService.getResultsForWordsInLanguages(Arrays.asList(languages), words, searchDTO);

        redirect.addFlashAttribute("languages", dictionaryService.findAllLanguages());
        redirect.addFlashAttribute("terms", words);
        redirect.addFlashAttribute("results", wordResults);

        return "redirect:/bulkOutput";
    }
}
