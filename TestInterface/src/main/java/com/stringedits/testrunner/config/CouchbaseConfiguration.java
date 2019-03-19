package com.stringedits.testrunner.config;

import static java.util.Arrays.asList;

import com.string.edits.couchbase.entities.CouchbaseBucketConfig;
import com.string.edits.couchbase.entities.CouchbaseClient;
import com.string.edits.couchbase.factories.CouchbaseClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchbaseConfiguration {

    @Autowired
    private CouchbaseClientFactory couchbaseClientFactory;

    @Bean
    @Qualifier("dictionary")
    public CouchbaseClient getDictionaryCouchbaseClient() {
        CouchbaseBucketConfig config1 =
            new CouchbaseBucketConfig("dictionary", "bogdan", "bogdan", asList("localhost", "local.couchbase"), "StringEditsDB", 1000);
        return couchbaseClientFactory.getCouchbaseClient(config1);
    }

    @Bean
    @Qualifier("resultsCache")
    public CouchbaseClient getResultsCacheCouchbaseClient() {
        CouchbaseBucketConfig config1 =
            new CouchbaseBucketConfig("resultsCache", "bogdan", "bogdan", asList("localhost", "local.couchbase"), "StringEditsDB", 1000);
        return couchbaseClientFactory.getCouchbaseClient(config1);
    }
}
