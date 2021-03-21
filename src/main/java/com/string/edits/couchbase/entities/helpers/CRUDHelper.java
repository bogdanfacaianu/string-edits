package com.string.edits.couchbase.entities.helpers;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CRUDHelper {

    @Autowired
    private Gson gson;

    public RawJsonDocument insert(Bucket bucket, String key, Object object) {
        return bucket.insert(getRawJsonDocument(key, object));
    }

    public RawJsonDocument insert(Bucket bucket, String key, int expiry, Object object) {
        return bucket.insert(getRawJsonDocument(key, expiry, object));
    }

    public RawJsonDocument replace(Bucket bucket, String key, Object object) {
        return bucket.replace(getRawJsonDocument(key, object));
    }

    public RawJsonDocument replace(Bucket bucket, String key, int expiry, Object object) {
        return bucket.replace(getRawJsonDocument(key, expiry, object));
    }

    public RawJsonDocument upsert(Bucket bucket, String key, Object object) {
        return bucket.upsert(getRawJsonDocument(key, object));
    }

    public RawJsonDocument upsert(Bucket bucket, String key, int expiry, Object object) {
        return bucket.upsert(getRawJsonDocument(key, expiry, object));
    }

    public JsonDocument insert(Bucket bucket, JsonDocument jsonDocument) {
        return bucket.insert(jsonDocument);
    }

    public JsonDocument replace(Bucket bucket, JsonDocument jsonDocument) {
        return bucket.replace(jsonDocument);
    }

    public JsonDocument upsert(Bucket bucket, JsonDocument jsonDocument) {
        return bucket.upsert(jsonDocument);
    }

    public String get(Bucket bucket, String key) {
        RawJsonDocument document = bucket.get(key, RawJsonDocument.class);
        return document == null ? null : document.content();
    }

    public JsonDocument remove(Bucket bucket, String key) {
        return bucket.remove(key);
    }
    
    private RawJsonDocument getRawJsonDocument(String key, Integer expiry, Object object) {
        return getRawJsonDocument(key, expiry, objectStringify(object));
    }

    private RawJsonDocument getRawJsonDocument(String key, Object object) {
        return getRawJsonDocument(key, null, objectStringify(object));
    }

    private RawJsonDocument getRawJsonDocument(String key, Integer expiry, String object) {
        return expiry == null ? RawJsonDocument.create(key, object) : RawJsonDocument.create(key, expiry, object);
    }

    private String objectStringify(Object object) {
        return object instanceof String ? (String) object : gson.toJson(object);
    }
}
