package com.string.edits.repository;

import static org.mockito.Mockito.verify;

import com.google.gson.GsonBuilder;
import com.string.edits.DictionaryTestHelp;
import com.string.edits.domain.TermQuery;
import com.string.edits.persistence.repository.ResultsCacheRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class DefaultResultsCacheRepositoryTest extends DictionaryTestHelp {

    ResultsCacheRepository cacheRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        gson = new GsonBuilder().create();
        cacheRepository = new DefaultResultsCacheRepository(couchbaseClient, gson);
    }

    @Test
    public void testSave() {
        TermQuery termQuery = createTermQueryResult();
        String json = gson.toJson(termQuery);

        cacheRepository.save(searchDTO, termQuery);

        verify(couchbaseClient).upsert(String.valueOf(searchDTO.hashCode()), json);
    }

    @Test
    public void testFindResult() {
        cacheRepository.findResult(searchDTO);

        verify(couchbaseClient).get(String.valueOf(searchDTO.hashCode()));
    }

    @Test
    public void testRemoveResult() {
        cacheRepository.removeEntry(searchDTO);

        verify(couchbaseClient).remove(String.valueOf(searchDTO.hashCode()));
    }

    @Test
    public void testDeleteAll() {
        cacheRepository.deleteAll();

        verify(couchbaseClient).flushBucket();
    }
}
