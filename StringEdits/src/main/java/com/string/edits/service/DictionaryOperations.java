package com.string.edits.service;

import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import com.string.edits.persistence.algorithm.StringDistanceAlgorithm;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictionaryOperations {

    @Autowired
    private DictionaryBuilder dictionaryBuilder;

    public int getDistanceBetween(String source, String target) {
        return StringDistanceAlgorithm.computeDistance(source, target);
    }

    public TermQuery getSolutions(ITransducer<Candidate> transducer, String searchTerm) {
        TermQuery minimumWordsWithDistances = dictionaryBuilder.getMinimumWordsWithDistances(transducer, searchTerm);
        return minimumWordsWithDistances;
    }

    public TermQuery returnResults(ITransducer<Candidate> transducer, String searchTerm) {
        return (TermQuery) getSolutions(transducer, searchTerm);
    }

    public Optional<ITransducer<Candidate>> buildTransducerForLanguage(Language language) {
        return dictionaryBuilder.buildDictionary(language);
    }
}
