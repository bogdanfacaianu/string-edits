package com.string.edits.couchbase.entities;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.string.edits.couchbase.entities.helpers.CRUDHelper;
import com.string.edits.couchbase.entities.helpers.HealthcheckHelper;
import com.string.edits.couchbase.entities.helpers.QueryHelper;

public abstract class DefaultCouchbaseClient {

    private Bucket bucket;
    private Cluster cluster;
    private boolean isCritical;
    private String name;
    private int pingTimeout;
    private HealthcheckHelper healthcheckHelper;
    private CRUDHelper crudHelper;
    private QueryHelper queryHelper;

    DefaultCouchbaseClient(Bucket bucket, Cluster cluster, CouchbaseBucketConfig couchbaseBucketConfig, HealthcheckHelper healthcheckHelper, CRUDHelper crudHelper, QueryHelper queryHelper) {
        this.bucket = bucket;
        this.cluster = cluster;
        this.isCritical = couchbaseBucketConfig.isCritical();
        this.name = couchbaseBucketConfig.getCouchbaseName();
        this.pingTimeout = couchbaseBucketConfig.getPingTimeout();
        this.crudHelper = crudHelper;
        this.healthcheckHelper = healthcheckHelper;
        this.queryHelper = queryHelper;
    }

    Bucket bucket() {
        return this.bucket;
    }

    Cluster cluster() {
        return this.cluster;
    }

    public boolean isCritical() {
        return this.isCritical;
    }

    public boolean isAlive() {
        return this.healthcheckHelper().isAlive(this.bucket(), this.pingTimeout());
    }

    public String getName() {
        return this.name;
    }

    public String getBucketName() {
        return this.bucket.name();
    }

    int pingTimeout() {
        return this.pingTimeout;
    }

    HealthcheckHelper healthcheckHelper() {
        return this.healthcheckHelper;
    }

    CRUDHelper crudHelper() {
        return this.crudHelper;
    }

    QueryHelper queryHelper() {
        return this.queryHelper;
    }
}
