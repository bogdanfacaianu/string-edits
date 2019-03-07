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

    public TermQuery getSolutions(Language language, String searchTerm, int maxDistance) {
        if (maxDistance < 1) {
            maxDistance = 5;
        }
        Optional<ITransducer<Candidate>> transducer = TransducerCreator.createSearchDictionaryWithMaxDistance(language.getDictionary(), maxDistance);
        if (transducer.isPresent()) {
            TermQuery minimumWordsWithDistances = getMinimumWordsWithDistances(transducer.get(), searchTerm);
            return minimumWordsWithDistances;
        }
        return new TermQuery(searchTerm);
    }

    public TermQuery returnResults(Language language, String searchTerm, int maxDistance) {
        return getSolutions(language, searchTerm, maxDistance);
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

        setEditsToMatches(termQuery, searchTerm);

        return termQuery;
    }

    private void setEditsToMatches(TermQuery termQuery, String searchTerm) {
        List<DistanceToWord> withEdits = new ArrayList<>();
        if (termQuery.getMatches().get(0).getDistance() > 0) {
            for (DistanceToWord dtw : termQuery.getMatches()) {
                withEdits.add(StringDistanceAlgorithm.computeLevenshteinDistance(searchTerm, dtw.getWord()));
            }
            termQuery.setMatches(withEdits);
        }
    }
}
