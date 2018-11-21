package com.string.edits;

import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import com.string.edits.persistence.algorithm.StringDistanceAlgorithm;
import com.string.edits.service.DictionaryBuilder;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloWorld {

    private static DictionaryBuilder dictionaryBuilder;

    @Autowired
    public HelloWorld(DictionaryBuilder dictionaryBuilder) {
        this.dictionaryBuilder = dictionaryBuilder;
    }

    public static int getDistanceBetween(String source, String target) {
        return StringDistanceAlgorithm.computeDistance(source, target);
    }

    public static TermQuery getSolutions(ITransducer<Candidate> transducer, String searchTerm) {
        TermQuery minimumWordsWithDistances = dictionaryBuilder.getMinimumWordsWithDistances(transducer, searchTerm);
        return minimumWordsWithDistances;
    }

    public static TermQuery returnResults(ITransducer<Candidate> transducer, String searchTerm) {
        return (TermQuery) getSolutions(transducer, searchTerm);
    }

    public static Optional<ITransducer<Candidate>> buildTransducerForLanguage(Language language) {
        return dictionaryBuilder.buildDictionary(language);
    }
}
