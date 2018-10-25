package com.string.edits;

import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;

import java.util.Collection;

public class DictionaryBuilder {

    public static ITransducer<Candidate> playWithTransducer(Collection<String> dictionary) {
        ITransducer<Candidate> transducer = new TransducerBuilder()
                .dictionary(dictionary)
                .algorithm(Algorithm.STANDARD)
                .defaultMaxDistance(5)
                .includeDistance(true)
                .build();

        return transducer;
    }

    public static void displayOutput(ITransducer<Candidate> transducer) {
        for (final String queryTerm : new String[] {"STAFICLA",  "GUMBO", "COPRAC"}) {
            System.out.println(
                    "+-------------------------------------------------------------------------------");
            System.out.printf("| Spelling Candidates for Query Term: \"%s\"%n", queryTerm);
            System.out.println(
                    "+-------------------------------------------------------------------------------");
            for (final Candidate candidate : transducer.transduce(queryTerm)) {
                System.out.printf("| d(\"%s\", \"%s\") = [%d]%n",
                        queryTerm,
                        candidate.term(),
                        candidate.distance());
            }
        }
    }
}
