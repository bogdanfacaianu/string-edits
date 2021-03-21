package com.string.edits.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.google.gson.GsonBuilder;
import com.string.edits.DictionaryTestHelp;
import com.string.edits.domain.Language;
import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import java.util.Optional;
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
        given_LanguageInRepository(true);
        given_DictionaryReturnsQueryResult(searchDTO, true);

        TermQuery resultsForWord = dictionaryService.getResultsForWord(searchDTO);

        verify(languageRepository).findLanguage(LANGUAGE);
        verify(dictionaryOperations).returnResults(createLanguage(true), searchDTO);

        assertPopulatedResultsAreReturned(resultsForWord);
    }

    @Test
    public void testLanguageAbset_thenEmptyTermQuery() {
        searchDTO.setLanguage(ABSENT_LANGUAGE);
        TermQuery resultsForWord = dictionaryService.getResultsForWord(searchDTO);

        verify(languageRepository).findLanguage(ABSENT_LANGUAGE);
        verifyZeroInteractions(dictionaryOperations);

        assertSearchDataIsReturned(resultsForWord, TERM, ABSENT_LANGUAGE, 0);
    }

    private void given_DictionaryReturnsQueryResult(SearchDTO searchDTO, boolean withTranspositions) {
        Language language = createLanguage(withTranspositions);
        Optional<TermQuery> result = createTermQueryResult();
        when(dictionaryOperations.returnResults(language, searchDTO)).thenReturn(result.get());
    }
}
