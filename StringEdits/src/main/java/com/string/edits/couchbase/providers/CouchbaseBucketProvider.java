package com.string.edits.couchbase.providers;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.string.edits.couchbase.entities.CouchbaseBucketConfig;
import org.springframework.stereotype.Component;

@Component
public class CouchbaseBucketProvider {

    public Bucket getBucket(CouchbaseBucketConfig couchbaseConfig, Cluster cluster) {
        return cluster.openBucket(couchbaseConfig.getBucket());
    }
}
