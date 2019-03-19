package com.string.edits.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.string.edits.DictionaryTestHelp;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class DictionaryOperationsTest extends DictionaryTestHelp {

    @InjectMocks
    private DictionaryOperations dictionaryOperations;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSolutionsFromCache() {
        Language language = createLanguage();
        given_ResultsCacheHasResult();

        TermQuery termQuery = dictionaryOperations.returnResults(language, searchDTO);

        verify(resultsCache, times(2)).findResult(searchDTO);

        assertPopulatedResultsAreReturned(termQuery);
    }

    @Test
    public void testGetSolutions() {
        Language language = createLanguage();
        given_ResultsChacheMiss();

        TermQuery termQuery = dictionaryOperations.returnResults(language, searchDTO);

        verify(resultsCache).findResult(searchDTO);
        verify(resultsCache).save(searchDTO, termQuery);

        assertPopulatedResultsAreReturnedFromTransducer(termQuery);
    }
}
