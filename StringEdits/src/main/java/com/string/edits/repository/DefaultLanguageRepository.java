package com.string.edits.repository;

import com.google.gson.Gson;
import com.string.edits.couchbase.entities.CouchbaseClient;
import com.string.edits.domain.Language;
import com.string.edits.persistence.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultLanguageRepository implements LanguageRepository {

    private final CouchbaseClient couchbaseClient;
    private final Gson gson;

    @Autowired
    public DefaultLanguageRepository(CouchbaseClient couchbaseClient, Gson gson) {
        this.couchbaseClient = couchbaseClient;
        this.gson = gson;
    }

    @Override
    public void save(Language language) {
        couchbaseClient.upsert(language.getName(), gson.toJson(language));
    }

    @Override
    public void addPatternToLanguage(String languageName, String pattern) {
        Language language = findLanguage(languageName);
        language.addPattern(pattern);
        save(language);
    }

    @Override
    public void delete(String languageName) {
        couchbaseClient.remove(languageName);
    }

    @Override
    public Language findLanguage(String languageName) {
        return gson.fromJson(couchbaseClient.get(languageName), Language.class);
    }
}
