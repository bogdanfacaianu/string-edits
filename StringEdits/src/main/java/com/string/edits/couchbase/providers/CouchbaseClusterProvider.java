package com.string.edits.couchbase.providers;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.string.edits.couchbase.entities.CouchbaseBucketConfig;
import org.springframework.stereotype.Component;

@Component
public class CouchbaseClusterProvider {

    public Cluster getCluster(DefaultCouchbaseEnvironment couchbaseEnvironment, CouchbaseBucketConfig couchbaseConfig) {
        Cluster cluster = CouchbaseCluster.create(couchbaseEnvironment, couchbaseConfig.getUriList());
        cluster.authenticate(couchbaseConfig.getUsername(), couchbaseConfig.getPassword());
        return cluster;
    }
}