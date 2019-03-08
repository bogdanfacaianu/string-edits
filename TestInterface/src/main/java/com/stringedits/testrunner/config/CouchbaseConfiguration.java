package com.stringedits.testrunner.config;

import static java.util.Arrays.asList;

import com.string.edits.couchbase.entities.CouchbaseBucketConfig;
import com.string.edits.couchbase.entities.CouchbaseClient;
import com.string.edits.couchbase.factories.CouchbaseClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchbaseConfiguration {

    @Autowired
    private CouchbaseClientFactory couchbaseClientFactory;

    @Bean
    public CouchbaseClient getCouchbaseClient() {
        CouchbaseBucketConfig config1 =
            new CouchbaseBucketConfig("bogdan", asList("localhost", "local.couchbase"), "StringEditsDB", 1000);
        return couchbaseClientFactory.getCouchbaseClient(config1);
    }
}
