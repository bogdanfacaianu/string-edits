package com.string.edits;

import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.domain.DistanceToWord;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DictionaryBuilder {

    private void computeResults(ITransducer<Candidate> transducer, String searchTerm) {

    }

    private TermQuery getMinimumWordsWithDistances(ITransducer<Candidate> transducer, String searchTerm) {
        List<DistanceToWord> results = new ArrayList<>();
        for (final Candidate candidate : transducer.transduce(searchTerm)) {
            DistanceToWord dtw = new DistanceToWord(candidate.term(), candidate.distance());
            results.add(dtw);
        }
        List<Integer> distances = (List<Integer>) results.stream().mapToInt(DistanceToWord::getDistance);
        int minimumDistance = distances.stream().min(Integer::compare).get();

        TermQuery termQuery = new TermQuery(searchTerm);
        List<DistanceToWord> filteredResults = results.stream()
            .filter(dtw -> dtw.getDistance() == minimumDistance)
            .collect(Collectors.toList());
        termQuery.setMatches(filteredResults);
        return termQuery;
    }


    public static void storeResults(ITransducer<Candidate> transducer, String searchTerm) {
        Language language = new Language(searchTerm);
    }

    public static void displayOutput(ITransducer<Candidate> transducer) {
        for (final String queryTerm : new String[]{"STAFICLA", "GUMBO", "COPRAC"}) {
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
