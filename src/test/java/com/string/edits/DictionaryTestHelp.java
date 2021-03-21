package com.string.edits;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import com.github.liblevenshtein.transducer.Algorithm;
import com.google.gson.Gson;
import com.string.edits.couchbase.entities.CouchbaseClient;
import com.string.edits.domain.DistanceToWord;
import com.string.edits.domain.EditType;
import com.string.edits.domain.Language;
import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import com.string.edits.domain.WordEdits;
import com.string.edits.persistence.repository.LanguageRepository;
import com.string.edits.persistence.repository.ResultsCacheRepository;
import java.util.Arrays;
import java.util.Optional;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class DictionaryTestHelp {

    protected final static String LANGUAGE = "language";
    protected final static String ABSENT_LANGUAGE = "inexistent-language";
    protected final static String TERM = "word";
    private final static Algorithm ALGORITHM = Algorithm.STANDARD;
    private final static int MAX_DISTANCE = 5;
    protected final static String SOLUTION1 = "word1";
    private final static String SOLUTION2 = "word2";
    protected final static String NEW_WORD = "word3";
    protected final static String TRANSPOSITIONS_WORD = "information";
    protected final static String TRANSPOSITIONS_TRIGGER = "ineoramtoin";

    protected SearchDTO searchDTO = new SearchDTO(LANGUAGE, TERM, ALGORITHM, MAX_DISTANCE);

    @Mock
    protected ResultsCacheRepository resultsCache;

    @Mock
    protected LanguageRepository languageRepository;

    @Mock
    protected CouchbaseClient couchbaseClient;

    @Autowired
    protected Gson gson;

    protected void given_LanguageInRepository(boolean withTranspositions) {
        Language language = createLanguage(withTranspositions);
        String jsonResult = gson.toJson(language);
        when(couchbaseClient.get(LANGUAGE)).thenReturn(jsonResult);
        when(languageRepository.findLanguage(LANGUAGE)).thenReturn(java.util.Optional.ofNullable(language));
    }

    protected Language createLanguage(boolean withTranspositions) {
        Language language = new Language(LANGUAGE);
        language.addWord(SOLUTION1);
        language.addWord(SOLUTION2);
        if (withTranspositions) {
            language.addWord(TRANSPOSITIONS_WORD);
        }
        return language;
    }

    protected Optional<TermQuery> createTermQueryResult() {
        TermQuery termQuery = new TermQuery(TERM, LANGUAGE);

        DistanceToWord dtw1 = new DistanceToWord(SOLUTION1, 3);
        DistanceToWord dtw2 = new DistanceToWord(SOLUTION2, 3);

        WordEdits we1 = new WordEdits(EditType.INSERTION, 2, 'c');
        WordEdits we2 = new WordEdits(EditType.DELETETION, 4, 'd');
        WordEdits we3 = new WordEdits(EditType.SUBSTITUTION, 2, 'a', 't');

        WordEdits we4 = new WordEdits(EditType.INSERTION, 2, 'c');
        WordEdits we5 = new WordEdits(EditType.TRANSPOSITION, 4, 'c', 'd', 6);
        WordEdits we6 = new WordEdits(EditType.SUBSTITUTION, 2, 'a', 't');

        dtw1.setEdits(Arrays.asList(we1, we2, we3));
        dtw2.setEdits(Arrays.asList(we4, we5, we6));

        termQuery.setMatches(Arrays.asList(dtw1, dtw2));

        return Optional.of(termQuery);
    }

    protected void given_ResultsCacheHasResult() {
        when(resultsCache.findResult(searchDTO)).thenReturn(createTermQueryResult());
    }

    protected void given_ResultsChacheMiss() {
        when(resultsCache.findResult(searchDTO)).thenReturn(Optional.empty());
    }

    protected void assertPopulatedResultsAreReturned(TermQuery resultsForWord) {
        assertSearchDataIsReturned(resultsForWord, TERM, LANGUAGE, 2);

        assertThat(resultsForWord.getMatches().get(0).getWord()).isEqualTo(SOLUTION1);
        assertThat(resultsForWord.getMatches().get(0).getEdits().size()).isEqualTo(3);
        assertThat(resultsForWord.getMatches().get(1).getWord()).isEqualTo(SOLUTION2);
        assertThat(resultsForWord.getMatches().get(1).getEdits().size()).isEqualTo(3);
    }

    protected void assertPopulatedResultsAreReturnedFromTransducer(TermQuery resultsForWord) {
        assertSearchDataIsReturned(resultsForWord, TERM, LANGUAGE, 2);

        assertThat(resultsForWord.getMatches().get(0).getWord()).isEqualTo(SOLUTION1);
        assertThat(resultsForWord.getMatches().get(0).getEdits().size()).isEqualTo(1);
        WordEdits we = resultsForWord.getMatches().get(0).getEdits().get(0);
        assertThat(we.getEditType()).isEqualTo(EditType.INSERTION);
        assertThat(we.getEditIndex()).isEqualTo(4);
        assertThat(we.getPotentialSolution()).isEqualTo('1');

        assertThat(resultsForWord.getMatches().get(1).getWord()).isEqualTo(SOLUTION2);
        assertThat(resultsForWord.getMatches().get(1).getEdits().size()).isEqualTo(1);
        we = resultsForWord.getMatches().get(1).getEdits().get(0);
        assertThat(we.getEditType()).isEqualTo(EditType.INSERTION);
        assertThat(we.getEditIndex()).isEqualTo(4);
        assertThat(we.getPotentialSolution()).isEqualTo('2');
    }

    protected void assertPopulatedResultsAreReturnedFromTransducer_withTranspositions(TermQuery resultsForWord) {
        assertSearchDataIsReturned(resultsForWord, TRANSPOSITIONS_TRIGGER, LANGUAGE, 1);

        assertThat(resultsForWord.getMatches().get(0).getWord()).isEqualTo(TRANSPOSITIONS_WORD);
        assertThat(resultsForWord.getMatches().get(0).getEdits().size()).isEqualTo(3);

        WordEdits we = resultsForWord.getMatches().get(0).getEdits().get(0);
        assertThat(we.getEditType()).isEqualTo(EditType.SUBSTITUTION);
        assertThat(we.getEditIndex()).isEqualTo(3);
        assertThat(we.getFoundCharacter()).isEqualTo('e');
        assertThat(we.getPotentialSolution()).isEqualTo('f');

        we = resultsForWord.getMatches().get(0).getEdits().get(1);
        assertThat(we.getEditType()).isEqualTo(EditType.TRANSPOSITION);
        assertThat(we.getEditIndex()).isEqualTo(6);
        assertThat(we.getFoundCharacter()).isEqualTo('a');
        assertThat(we.getPotentialSolution()).isEqualTo('m');
        assertThat(we.getTranspositionIndex()).isEqualTo(7);

        we = resultsForWord.getMatches().get(0).getEdits().get(2);
        assertThat(we.getEditType()).isEqualTo(EditType.TRANSPOSITION);
        assertThat(we.getEditIndex()).isEqualTo(10);
        assertThat(we.getFoundCharacter()).isEqualTo('i');
        assertThat(we.getPotentialSolution()).isEqualTo('o');
        assertThat(we.getTranspositionIndex()).isEqualTo(9);
    }

    protected void assertSearchDataIsReturned(TermQuery result, String term, String language, int noOfMatches) {
        assertThat(result.getTerm()).isEqualTo(term);
        assertThat(result.getLanguage()).isEqualTo(language);
        if (noOfMatches == 0) {
            assertThat(result.getMatches()).isNull();
        } else {
            assertThat(result.getMatches().size()).isEqualTo(noOfMatches);
        }
    }
}
