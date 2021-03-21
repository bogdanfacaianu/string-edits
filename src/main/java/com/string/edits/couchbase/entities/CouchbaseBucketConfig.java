package com.string.edits.couchbase.entities;

import java.util.List;

public class CouchbaseBucketConfig {

    private String bucket;
    private String username;
    private String password;
    private List<String> uriList;
    private boolean isCritical;
    private String couchbaseName;
    private int pingTimeout;

    public CouchbaseBucketConfig(String bucketDetails, List<String> uriList, String couchbaseName, int pingTimeout) {
        this.bucket = bucketDetails;
        this.username = bucketDetails;
        this.password = bucketDetails;
        this.uriList = uriList;
        this.isCritical = false;
        this.couchbaseName = couchbaseName;
        this.pingTimeout = pingTimeout;
    }

    public CouchbaseBucketConfig(String usernameAndBucket, String password, List<String> uriList, String couchbaseName, int pingTimeout) {
        this.bucket = usernameAndBucket;
        this.username = usernameAndBucket;
        this.password = password;
        this.uriList = uriList;
        this.isCritical = false;
        this.couchbaseName = couchbaseName;
        this.pingTimeout = pingTimeout;
    }

    public CouchbaseBucketConfig(String bucket, String username, String password, List<String> uriList, String couchbaseName, int pingTimeout) {
        this.bucket = bucket;
        this.username = username;
        this.password = password;
        this.uriList = uriList;
        this.isCritical = false;
        this.couchbaseName = couchbaseName;
        this.pingTimeout = pingTimeout;
    }

    public String getBucket() {
        return bucket;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getUriList() {
        return uriList;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public String getCouchbaseName() {
        return couchbaseName;
    }

    public int getPingTimeout() {
        return pingTimeout;
    }
}
