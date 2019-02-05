package com.stringedits.testrunner.config;

import com.string.edits.couchbase.entities.CouchbaseBucketConfig;
import com.string.edits.couchbase.entities.CouchbaseClient;
import com.string.edits.couchbase.factories.CouchbaseClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Arrays.asList;

@Configuration
public class CouchbaseConfiguration {

    @Autowired
    private CouchbaseClientFactory couchbaseClientFactory;

    @Bean
    public CouchbaseClient getCouchbaseClient() {
        CouchbaseBucketConfig config1 =
            new CouchbaseBucketConfig("bogdan", asList("localhost"), "StringEditsDB", 1000);
        return couchbaseClientFactory.getCouchbaseClient(config1);
    }
}
