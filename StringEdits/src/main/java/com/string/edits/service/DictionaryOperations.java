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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictionaryOperations {

    private static final Logger LOG = LoggerFactory.getLogger(DictionaryOperations.class);

    private final ResultsCacheRepository resultsCache;

    @Autowired
    public DictionaryOperations(ResultsCacheRepository resultsCache) {
        this.resultsCache = resultsCache;
    }

    TermQuery returnResults(Language language, SearchDTO searchDTO) {
        if(resultsCache.findResult(searchDTO).isPresent()) {
            LOG.info("Retrieving results from cache for {}", searchDTO);
            return resultsCache.findResult(searchDTO).get();
        }
        TermQuery result = getSolutions(language, searchDTO);
        resultsCache.save(searchDTO, result);
        return result;
    }

    private TermQuery getSolutions(Language language, SearchDTO searchDTO) {
        if (searchDTO.getMaxDistance() < 1) {
            LOG.warn("Maximum distance provided {} was below 1, defaulting to 5", searchDTO.getMaxDistance());
            searchDTO.setMaxDistance(5);
        }
        TermQuery result = new TermQuery(searchDTO.getSearchTerm(), searchDTO.getLanguage());
        Optional<ITransducer<Candidate>> transducer =
            TransducerCreator.createDictionarySearch(language.getDictionary(), searchDTO.getAlgorithm(), searchDTO.getMaxDistance());
        transducer.ifPresent(candidateITransducer -> getMinimumWordsWithDistances(candidateITransducer, result));
        return result;
    }

    private void getMinimumWordsWithDistances(ITransducer<Candidate> transducer, TermQuery result) {
        List<DistanceToWord> results = new ArrayList<>();
        LOG.debug("Retrieving results from transducer {} for {}", transducer, result.getTerm());
        for (final Candidate candidate : transducer.transduce(result.getTerm())) {
            DistanceToWord dtw = new DistanceToWord(candidate.term(), candidate.distance());
            results.add(dtw);
        }

        List<Integer> distances = new ArrayList<>();
        results.forEach(dtw -> distances.add(dtw.getDistance()));
        int minimumDistance = distances.stream().min(Integer::compare).orElse(-1);
        LOG.info("Minimum distance found is {}", minimumDistance);

        if (minimumDistance > 0) {
            List<DistanceToWord> filteredResults = results.stream()
                .filter(dtw -> dtw.getDistance() == minimumDistance)
                .collect(Collectors.toList());
            result.setMatches(filteredResults);
        }

        setEditsToMatches(result);
    }

    private void setEditsToMatches(TermQuery result) {
        List<DistanceToWord> withEdits = new ArrayList<>();
        if (result.getMatches() != null && result.getMatches().get(0).getDistance() > 0) {
            LOG.info("Computing edits required");
            for (DistanceToWord dtw : result.getMatches()) {
                withEdits.add(StringDistanceAlgorithm.computeLevenshteinDistance(result.getTerm(), dtw.getWord()));
            }
            result.setMatches(withEdits);
        }
    }

    public void clearCache() {
        LOG.info("Deleting cache");
        resultsCache.deleteAll();
    }
}
