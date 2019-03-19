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
import java.util.HashSet;
import java.util.Set;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class DictionaryTestHelp {

    protected final static String LANGUAGE = "language";
    protected final static String ABSENT_LANGUAGE = "inexistent-language";
    private final static String TERM = "word";
    private final static Algorithm ALGORITHM = Algorithm.STANDARD;
    private final static int MAX_DISTANCE = 5;
    protected final static String SOLUTION1 = "word1";
    protected final static String SOLUTION2 = "word2";
    protected final static String NEW_WORD = "word3";

    protected SearchDTO searchDTO = new SearchDTO(LANGUAGE, TERM, ALGORITHM, MAX_DISTANCE);

    @Mock
    protected ResultsCacheRepository resultsCache;

    @Mock
    protected LanguageRepository languageRepository;

    @Mock
    protected CouchbaseClient couchbaseClient;

    @Autowired
    protected Gson gson;

    protected void given_LanguageInRepository() {
        Language language = createLanguage();
        String jsonResult = gson.toJson(language);
        when(couchbaseClient.get(LANGUAGE)).thenReturn(jsonResult);
        when(languageRepository.findLanguage(LANGUAGE)).thenReturn(language);
    }

    protected Language createLanguage() {
        Language language = new Language(LANGUAGE);
        Set<String> dictionary = new HashSet<>();
        dictionary.add(SOLUTION1);
        dictionary.add(SOLUTION2);
        language.setDictionary(dictionary);
        return language;
    }

    protected TermQuery createTermQueryResult() {
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

        return termQuery;
    }

    protected void given_ResultsCacheHasResult() {
        when(resultsCache.findResult(searchDTO)).thenReturn(createTermQueryResult());
    }

    protected void given_ResultsChacheMiss() {
        when(resultsCache.findResult(searchDTO)).thenReturn(null);
    }

    protected void assertPopulatedResultsAreReturned(TermQuery resultsForWord) {
        assertThat(resultsForWord.getTerm()).isEqualTo(TERM);
        assertThat(resultsForWord.getLanguage()).isEqualTo(LANGUAGE);
        assertThat(resultsForWord.getMatches().size()).isEqualTo(2);
        assertThat(resultsForWord.getMatches().get(0).getWord()).isEqualTo(SOLUTION1);
        assertThat(resultsForWord.getMatches().get(0).getEdits().size()).isEqualTo(3);
        assertThat(resultsForWord.getMatches().get(1).getWord()).isEqualTo(SOLUTION2);
        assertThat(resultsForWord.getMatches().get(1).getEdits().size()).isEqualTo(3);
    }

    protected void assertEmptyResultReturned(TermQuery resultsForWord) {
        assertThat(resultsForWord.getTerm()).isEqualTo(TERM);
        assertThat(resultsForWord.getLanguage()).isEqualTo(ABSENT_LANGUAGE);
        assertThat(resultsForWord.getMatches()).isNull();
    }

    protected void assertPopulatedResultsAreReturnedFromTransducer(TermQuery resultsForWord) {
        assertThat(resultsForWord.getTerm()).isEqualTo(TERM);
        assertThat(resultsForWord.getLanguage()).isEqualTo(LANGUAGE);
        assertThat(resultsForWord.getMatches().size()).isEqualTo(2);

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
}
