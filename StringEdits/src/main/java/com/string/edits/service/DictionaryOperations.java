package com.string.edits.service;

import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.domain.DistanceToWord;
import com.string.edits.domain.Language;
import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import com.string.edits.persistence.algorithm.StringDistanceAlgorithm;
import com.string.edits.persistence.repository.ResultsCacheRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictionaryOperations {

    private final ResultsCacheRepository resultsCache;

    @Autowired
    public DictionaryOperations(ResultsCacheRepository resultsCache) {
        this.resultsCache = resultsCache;
    }

    public TermQuery getSolutions(Language language, SearchDTO searchDTO) {
        if (searchDTO.getMaxDistance() < 1) {
            searchDTO.setMaxDistance(5);
        }
        TermQuery result = new TermQuery(searchDTO.getSearchTerm());
        result.setLanguage(searchDTO.getLanguage());
        Optional<ITransducer<Candidate>> transducer =
            TransducerCreator.createDictionarySearch(language.getDictionary(), searchDTO.getAlgorithm(), searchDTO.getMaxDistance());
        transducer.ifPresent(candidateITransducer -> getMinimumWordsWithDistances(candidateITransducer, result));
        return result;
    }

    TermQuery returnResults(Language language, SearchDTO searchDTO) {
        if(resultsCache.findResult(searchDTO) != null) {
            return resultsCache.findResult(searchDTO);
        }
        TermQuery result = getSolutions(language, searchDTO);
        resultsCache.save(searchDTO, result);
        return result;
    }

    private void getMinimumWordsWithDistances(ITransducer<Candidate> transducer, TermQuery result) {
        List<DistanceToWord> results = new ArrayList<>();
        for (final Candidate candidate : transducer.transduce(result.getTerm())) {
            DistanceToWord dtw = new DistanceToWord(candidate.term(), candidate.distance());
            results.add(dtw);
        }
        List<Integer> distances = new ArrayList<>();
        results.forEach(dtw -> distances.add(dtw.getDistance()));
        int minimumDistance = distances.stream().min(Integer::compare).orElse(-1);

        if (minimumDistance < 0) {
            result.setMatches(results);
        } else {
            List<DistanceToWord> filteredResults = results.stream()
                .filter(dtw -> dtw.getDistance() == minimumDistance)
                .collect(Collectors.toList());
            result.setMatches(filteredResults);
        }

        setEditsToMatches(result);
    }

    private void setEditsToMatches(TermQuery result) {
        List<DistanceToWord> withEdits = new ArrayList<>();
        if (!result.getMatches().isEmpty() && result.getMatches().get(0).getDistance() > 0) {
            for (DistanceToWord dtw : result.getMatches()) {
                withEdits.add(StringDistanceAlgorithm.computeLevenshteinDistance(result.getTerm(), dtw.getWord()));
            }
            result.setMatches(withEdits);
        }
    }

    public void clearCache() {
        resultsCache.deleteAll();
    }
}
