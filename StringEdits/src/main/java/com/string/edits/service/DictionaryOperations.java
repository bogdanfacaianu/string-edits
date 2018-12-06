package com.string.edits.service;

import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.domain.DistanceToWord;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import com.string.edits.persistence.algorithm.StringDistanceAlgorithm;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DictionaryOperations {

    public int getDistanceBetween(String source, String target) {
        return StringDistanceAlgorithm.computeDistance(source, target);
    }

    public TermQuery getSolutions(Language language, String searchTerm) {
        Optional<ITransducer<Candidate>> transducer = TransducerCreator.createSearchDictionary(language.getDictionary());
        if (transducer.isPresent()) {
            TermQuery minimumWordsWithDistances = getMinimumWordsWithDistances(transducer.get(), searchTerm);
            return minimumWordsWithDistances;
        }
        return new TermQuery(searchTerm);
    }

    public TermQuery returnResults(Language language, String searchTerm) {
        return getSolutions(language, searchTerm);
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
