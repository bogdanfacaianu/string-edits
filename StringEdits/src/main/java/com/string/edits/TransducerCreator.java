package com.string.edits;

import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;
import com.string.edits.persistence.repository.LanguageRepository;

import java.util.Collection;

public class TransducerCreator {

    private LanguageRepository languageRepository;

    public TransducerCreator(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public ITransducer<Candidate> createSearchDictionary(Collection<String> dictionary) {
        ITransducer<Candidate> transducer = new TransducerBuilder()
                .dictionary(dictionary)
                .algorithm(Algorithm.STANDARD)
                .defaultMaxDistance(5)
                .includeDistance(true)
                .build();

        return transducer;
    }

    public ITransducer<Candidate> createSearchDictionaryWithAlgorithmType(Collection<String> dictionary, Algorithm algorithmType) {
        ITransducer<Candidate> transducer = new TransducerBuilder()
                .dictionary(dictionary)
                .algorithm(algorithmType)
                .defaultMaxDistance(5)
                .includeDistance(true)
                .build();

        return transducer;
    }

    public ITransducer<Candidate> createSearchDictionaryWithAlgorithmTypeAndMaxDistance(Collection<String> dictionary, Algorithm algorithmType, int maxDistance) {
        ITransducer<Candidate> transducer = new TransducerBuilder()
                .dictionary(dictionary)
                .algorithm(algorithmType)
                .defaultMaxDistance(maxDistance)
                .includeDistance(true)
                .build();

        return transducer;
    }

}
