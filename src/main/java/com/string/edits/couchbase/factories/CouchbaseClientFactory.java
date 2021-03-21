package com.string.edits.couchbase.factories;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.string.edits.couchbase.entities.CouchbaseBucketConfig;
import com.string.edits.couchbase.entities.CouchbaseClient;
import com.string.edits.couchbase.entities.helpers.CRUDHelper;
import com.string.edits.couchbase.entities.helpers.HealthcheckHelper;
import com.string.edits.couchbase.entities.helpers.QueryHelper;
import com.string.edits.couchbase.providers.CouchbaseBucketProvider;
import com.string.edits.couchbase.providers.CouchbaseClusterProvider;
import com.string.edits.couchbase.providers.CouchbaseEnvironmentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CouchbaseClientFactory {

    @Autowired
    private CouchbaseBucketProvider couchbaseBucketProvider;

    @Autowired
    private CouchbaseClusterProvider couchbaseClusterProvider;

    @Autowired
    private CouchbaseEnvironmentProvider couchbaseEnvironmentProvider;

    @Autowired
    private HealthcheckHelper healthcheckHelper;

    @Autowired
    private CRUDHelper crudHelper;

    @Autowired
    private QueryHelper queryHelper;

    public CouchbaseClient getCouchbaseClient(CouchbaseBucketConfig couchbaseBucketConfig) {

        DefaultCouchbaseEnvironment couchbaseEnvironment = couchbaseEnvironmentProvider.getCouchbaseEnvironment();
        Cluster cluster = couchbaseClusterProvider.getCluster(couchbaseEnvironment, couchbaseBucketConfig);
        Bucket bucket = couchbaseBucketProvider.getBucket(couchbaseBucketConfig, cluster);

        return new CouchbaseClient(bucket, cluster, couchbaseBucketConfig, healthcheckHelper, crudHelper, queryHelper);
    }
}
