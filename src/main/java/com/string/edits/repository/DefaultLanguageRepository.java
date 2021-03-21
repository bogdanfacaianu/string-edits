package com.string.edits.repository;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.string.edits.couchbase.entities.CouchbaseClient;
import com.string.edits.domain.Language;
import com.string.edits.persistence.repository.LanguageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultLanguageRepository implements LanguageRepository {

    private final CouchbaseClient couchbaseClient;
    private final Gson gson;

    @Autowired
    public DefaultLanguageRepository(@Qualifier("dictionary") CouchbaseClient couchbaseClient, Gson gson) {
        this.couchbaseClient = couchbaseClient;
        this.gson = gson;
    }

    @Override
    public void save(Language language) {
        couchbaseClient.upsert(language.getName(), gson.toJson(language));
    }

    @Override
    public void addWordToLanguage(String languageName, String word) {
        Optional<Language> language = findLanguage(languageName);
        language.ifPresent(lang -> {
           lang.addWord(word);
           save(lang);
        });
    }

    @Override
    public void delete(String languageName) {
        couchbaseClient.remove(languageName);
    }

    @Override
    public Optional<Language> findLanguage(String languageName) {
        return Optional.ofNullable(gson.fromJson(couchbaseClient.get(languageName), Language.class));
    }

    @Override
    public void removeWordFromLanguage(String languageName, String word) {
        Optional<Language> language = findLanguage(languageName);
        language.ifPresent(lang -> {
            lang.removeWord(word);
            save(lang);
        });
    }

    @Override
    public List<String> findAllLanguages() {
        List<String> languages = new ArrayList<>();
        for (String key : couchbaseClient.getField("name")) {
            JsonObject jsonObject = gson.fromJson(key, JsonObject.class);
            languages.add(jsonObject.get("name").getAsString());
        }
        return languages;
    }

    @Override
    public void deleteAll() {
        couchbaseClient.flushBucket();
    }
}
