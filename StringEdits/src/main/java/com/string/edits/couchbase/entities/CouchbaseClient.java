package com.string.edits.couchbase.entities;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.query.N1qlParams;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.ParameterizedN1qlQuery;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.view.ViewResult;
import com.string.edits.couchbase.entities.helpers.CRUDHelper;
import com.string.edits.couchbase.entities.helpers.HealthcheckHelper;
import com.string.edits.couchbase.entities.helpers.QueryHelper;

public class CouchbaseClient extends DefaultCouchbaseClient {

    public CouchbaseClient(Bucket bucket, Cluster cluster, CouchbaseBucketConfig couchbaseBucketConfig, HealthcheckHelper healthcheckHelper, CRUDHelper crudHelper, QueryHelper queryHelper) {
        super(bucket, cluster, couchbaseBucketConfig, healthcheckHelper, crudHelper, queryHelper);
    }

    public RawJsonDocument upsert(String key, Object object) {
        return this.crudHelper().upsert(this.bucket(), key, object);
    }

    public JsonDocument upsert(JsonDocument jsonDocument) {
        return this.crudHelper().upsert(this.bucket(), jsonDocument);
    }

    public RawJsonDocument replace(String key, Object object) {
        return this.crudHelper().replace(this.bucket(), key, object);
    }

    public String get(String key) {
        return this.crudHelper().get(this.bucket(), key);
    }

    public JsonDocument remove(String key) {
        return this.crudHelper().remove(this.bucket(), key);
    }
}
