package com.string.edits.repository;

import static org.mockito.Mockito.verify;

import com.google.gson.GsonBuilder;
import com.string.edits.DictionaryTestHelp;
import com.string.edits.domain.Language;
import com.string.edits.persistence.repository.LanguageRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class DefaultLanguageRepositoryTest extends DictionaryTestHelp {

    private LanguageRepository languageRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        gson = new GsonBuilder().create();
        languageRepository = new DefaultLanguageRepository(couchbaseClient, gson);
    }

    @Test
    public void testSave() {
        Language language = createLanguage();
        String jsonResult = gson.toJson(language);
        languageRepository.save(language);

        verify(couchbaseClient).upsert(language.getName(), jsonResult);
    }

    @Test
    public void testFindLanguage() {
        languageRepository.findLanguage(LANGUAGE);

        verify(couchbaseClient).get(LANGUAGE);
    }

    @Test
    public void testAddWordToLanguage() {
        given_LanguageInRepository();
        Language language = createLanguage();

        language.removeWord(SOLUTION2);
        language.addWord(NEW_WORD);
        language.addWord(SOLUTION2);
        languageRepository.addWordToLanguage(LANGUAGE, NEW_WORD);


        verify(couchbaseClient).get(LANGUAGE);

        // little hack to bypass hashcodes in language hashset
        String jsonResult = gson.toJson(language);
        StringBuilder json = new StringBuilder(jsonResult);
        json.setCharAt(46,'2');
        json.setCharAt(54,'3');

        verify(couchbaseClient).upsert(LANGUAGE, json.toString());
    }

    @Test
    public void testDeleteLanguage() {
        languageRepository.delete(LANGUAGE);

        verify(couchbaseClient).remove(LANGUAGE);
    }

    @Test
    public void testRemoveWordFromLanguage() {
        given_LanguageInRepository();
        Language language = createLanguage();

        language.removeWord(SOLUTION1);
        languageRepository.removeWordFromLanguage(LANGUAGE, SOLUTION1);

        verify(couchbaseClient).get(LANGUAGE);
        String jsonResult = gson.toJson(language);
        verify(couchbaseClient).upsert(LANGUAGE, jsonResult);
    }

    @Test
    public void testFindAllLanguages() {
        languageRepository.findAllLanguages();

        verify(couchbaseClient).getField("name");
    }

    @Test
    public void testDeleteAll() {
        languageRepository.deleteAll();

        verify(couchbaseClient).flushBucket();
    }
}