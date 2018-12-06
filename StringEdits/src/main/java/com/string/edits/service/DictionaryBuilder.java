package com.string.edits.service;

import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.domain.DistanceToWord;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictionaryBuilder {

    private final TransducerCreator transducerCreator;
    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryBuilder(TransducerCreator transducerCreator, DictionaryService dictionaryService) {
        this.transducerCreator = transducerCreator;
        this.dictionaryService = dictionaryService;
    }

    public Optional<ITransducer<Candidate>> buildDictionary(Language language) {
        dictionaryService.saveLanguage(language);
        return transducerCreator.createSearchDictionary(language.getName());

    }

    public TermQuery getMinimumWordsWithDistances(ITransducer<Candidate> transducer, String searchTerm) {
        List<DistanceToWord> results = new ArrayList<>();
        for (final Candidate candidate : transducer.transduce(searchTerm)) {
            DistanceToWord dtw = new DistanceToWord(candidate.term(), candidate.distance());
            results.add(dtw);
        }
        List<Integer> distances = new ArrayList<>();
        results.forEach(dtw -> distances.add(dtw.getDistance()));
        int minimumDistance = distances.stream().min(Integer::compare).orElse(-1);

        TermQuery termQuery = new TermQuery(searchTerm);
        if (minimumDistance < 0) {
            termQuery.setMatches(results);
        } else {
            List<DistanceToWord> filteredResults = results.stream()
                .filter(dtw -> dtw.getDistance() == minimumDistance)
                .collect(Collectors.toList());
            termQuery.setMatches(filteredResults);
        }
        return termQuery;
    }
}
