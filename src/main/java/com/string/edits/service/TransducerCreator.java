package com.string.edits.service;

import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;
import java.util.Optional;
import java.util.Set;

public class TransducerCreator {

    public static Optional<ITransducer<Candidate>> createDictionarySearch(Set<String> dictionaryEntries, Algorithm algorithmType, int maxDistance) {
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
