package com.string.edits;

import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;

import com.string.edits.service.DictionaryService;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransducerCreator {

    private final DictionaryService dictionaryService;

    @Autowired
    public TransducerCreator(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public Optional<ITransducer<Candidate>> createSearchDictionary(String languageName) {
        Set<String> dictionaryEntries = dictionaryService.getDictionaryEntries(languageName);
        if (dictionaryEntries.isEmpty()) {
            return Optional.empty();
        }
        ITransducer<Candidate> transducer = new TransducerBuilder()
                .dictionary(dictionaryEntries)
                .algorithm(Algorithm.STANDARD)
                .defaultMaxDistance(5)
                .includeDistance(true)
                .build();

        return Optional.ofNullable(transducer);
    }

    public Optional<ITransducer<Candidate>> createSearchDictionaryWithAlgorithmType(String languageName, Algorithm algorithmType) {
        Set<String> dictionaryEntries = dictionaryService.getDictionaryEntries(languageName);
        if (dictionaryEntries.isEmpty()) {
            return Optional.empty();
        }
        ITransducer<Candidate> transducer = new TransducerBuilder()
            .dictionary(dictionaryEntries)
            .algorithm(algorithmType)
            .defaultMaxDistance(5)
            .includeDistance(true)
            .build();

        return Optional.ofNullable(transducer);
    }

    public Optional<ITransducer<Candidate>> createSearchDictionaryWithAlgorithmTypeAndMaxDistance(String languageName, Algorithm algorithmType, int maxDistance) {
        Set<String> dictionaryEntries = dictionaryService.getDictionaryEntries(languageName);
        if (dictionaryEntries.isEmpty()) {
            return Optional.empty();
        }
        ITransducer<Candidate> transducer = new TransducerBuilder()
            .dictionary(dictionaryEntries)
            .algorithm(algorithmType)
            .defaultMaxDistance(maxDistance)
            .includeDistance(true)
            .build();

        return Optional.ofNullable(transducer);
    }

}
