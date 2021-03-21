package com.string.edits.couchbase.entities.helpers;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.query.*;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QueryHelper {

    public ViewResult viewQuery(Bucket bucket, String design, String view, String key) {
        return bucket.query(getViewQuery(design, view, key));
    }

    private ViewQuery getViewQuery(String design, String view, String key) {
        ViewQuery viewQuery = ViewQuery.from(design, view);

        Optional.ofNullable(key).ifPresent(viewQuery::key);

        return viewQuery;
    }
}
