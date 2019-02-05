package com.string.edits.couchbase.providers;

import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CouchbaseEnvironmentProvider {

    @Autowired
    private CouchbaseClusterConfigProvider couchbaseClusterConfigProvider;

    private DefaultCouchbaseEnvironment couchbaseEnvironment;

    public DefaultCouchbaseEnvironment getCouchbaseEnvironment() {
        if(couchbaseEnvironment == null) {
            initialize();
        }
        return couchbaseEnvironment;
    }

    private void initialize() {
        DefaultCouchbaseEnvironment.Builder builder = DefaultCouchbaseEnvironment.builder();

        builder.kvTimeout(couchbaseClusterConfigProvider.getKvTimeout());
        builder.viewTimeout(couchbaseClusterConfigProvider.getViewTimeout());
        builder.queryTimeout(couchbaseClusterConfigProvider.getQueryTimeout());
        builder.searchTimeout(couchbaseClusterConfigProvider.getSearchTimeout());
        builder.analyticsTimeout(couchbaseClusterConfigProvider.getAnalyticsTimeout());
        builder.connectTimeout(couchbaseClusterConfigProvider.getConnectTimeout());
        builder.disconnectTimeout(couchbaseClusterConfigProvider.getDisconnectTimeout());
        builder.socketConnectTimeout(couchbaseClusterConfigProvider.getSocketConnectTimeout());

        couchbaseEnvironment = builder.build();
    }
}
