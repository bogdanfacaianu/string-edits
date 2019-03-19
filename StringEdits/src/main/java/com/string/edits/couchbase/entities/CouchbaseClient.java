package com.string.edits.couchbase.entities;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.string.edits.couchbase.entities.helpers.CRUDHelper;
import com.string.edits.couchbase.entities.helpers.HealthcheckHelper;
import com.string.edits.couchbase.entities.helpers.QueryHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<String> getField(String field) {
        List<String> keys = new ArrayList<>();

        for (N1qlQueryRow result: this.bucket().query(N1qlQuery.simple(String.format("SELECT %s FROM %s", field, this.getBucketName())))) {
            keys.add(result.value().toString());
        }

        return keys;
    }

    public void flushBucket() {
        this.bucket().query(N1qlQuery.simple(String.format("DELETE FROM %s", this.getBucketName())));
    }
}
