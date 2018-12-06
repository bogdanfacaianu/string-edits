package com.stringedits.testrunner.config;

import static java.util.Arrays.asList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thehutgroup.fusion.core.services.HealthcheckService;
import com.thehutgroup.fusion.couchbase.entities.CouchbaseBucketConfig;
import com.thehutgroup.fusion.couchbase.entities.CouchbaseClient;
import com.thehutgroup.fusion.couchbase.factories.CouchbaseClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchbaseConfiguration {

    @Bean
    public CouchbaseClient getCouchbaseClient() {
        CouchbaseBucketConfig config =
            new CouchbaseBucketConfig("bogdan", asList("localhost"), "StringEditsDB", 1000);
        return CouchbaseClientFactory.getInstance().getCouchbaseClient(config);
    }

    @Bean
    public HealthcheckService getHealthCheckService() {
        return HealthcheckService.getInstance();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }
}
