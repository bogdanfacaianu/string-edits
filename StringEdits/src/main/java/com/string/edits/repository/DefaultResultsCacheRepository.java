package com.string.edits.repository;

import com.google.gson.Gson;
import com.string.edits.couchbase.entities.CouchbaseClient;
import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import com.string.edits.persistence.repository.ResultsCacheRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultResultsCacheRepository implements ResultsCacheRepository {

    private final CouchbaseClient couchbaseClient;
    private final Gson gson;

    @Autowired
    public DefaultResultsCacheRepository(@Qualifier("resultsCache") CouchbaseClient couchbaseClient, Gson gson) {
        this.couchbaseClient = couchbaseClient;
        this.gson = gson;
    }

    @Override
    public void save(SearchDTO searchDTO, TermQuery termQuery) {
        couchbaseClient.upsert(String.valueOf(searchDTO.hashCode()), gson.toJson(termQuery));
    }

    @Override
    public Optional<TermQuery> findResult(SearchDTO searchDTO) {
        String key = String.valueOf(searchDTO.hashCode());
        return Optional.ofNullable(gson.fromJson(couchbaseClient.get(key), TermQuery.class));
    }

    @Override
    public void removeEntry(SearchDTO searchDTO) {
        couchbaseClient.remove(String.valueOf(searchDTO.hashCode()));
    }

    @Override
    public void deleteAll() {
        couchbaseClient.flushBucket();
    }
}
