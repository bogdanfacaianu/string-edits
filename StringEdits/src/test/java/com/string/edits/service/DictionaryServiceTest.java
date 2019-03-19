package com.string.edits.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.google.gson.GsonBuilder;
import com.string.edits.DictionaryTestHelp;
import com.string.edits.domain.Language;
import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DictionaryServiceTest extends DictionaryTestHelp {

    @Mock
    private DictionaryOperations dictionaryOperations;

    @InjectMocks
    private DictionaryService dictionaryService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        gson = new GsonBuilder().create();
    }

    @Test
    public void testGetResultsForWord() {
        given_LanguageInRepository();
        given_DictionaryReturnsQueryResult(searchDTO);

        TermQuery resultsForWord = dictionaryService.getResultsForWord(searchDTO);

        verify(languageRepository).findLanguage(LANGUAGE);
        verify(dictionaryOperations).returnResults(createLanguage(), searchDTO);

        assertPopulatedResultsAreReturned(resultsForWord);
    }

    @Test
    public void testLanguageAbset_thenEmptyTermQuery() {
        searchDTO.setLanguage(ABSENT_LANGUAGE);
        TermQuery resultsForWord = dictionaryService.getResultsForWord(searchDTO);

        verify(languageRepository).findLanguage(ABSENT_LANGUAGE);
        verifyZeroInteractions(dictionaryOperations);

        assertEmptyResultReturned(resultsForWord);
    }

    private void given_DictionaryReturnsQueryResult(SearchDTO searchDTO) {
        Language language = createLanguage();
        TermQuery result = createTermQueryResult();
        when(dictionaryOperations.returnResults(language, searchDTO)).thenReturn(result);
    }
}
