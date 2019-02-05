package com.string.edits.couchbase.providers;

import org.springframework.stereotype.Component;

@Component
class CouchbaseClusterConfigProvider {

    private static final int KV_TIMEOUT = 2000;
    private static final int VIEW_TIMEOUT = 60000;
    private static final int QUERY_TIMEOUT = 60000;
    private static final int SEARCH_TIMEOUT = 60000;
    private static final int ANALYTICS_TIMEOUT = 60000;
    private static final int CONNECT_TIMEOUT = 3000;
    private static final int DISCONNECT_TIMEOUT = 3000;
    private static final int SOCKET_CONNECT_TIMEOUT = 2000;

    int getKvTimeout() {
        return KV_TIMEOUT;
    }

    int getViewTimeout() {
        return VIEW_TIMEOUT;
    }

    int getQueryTimeout() {
        return QUERY_TIMEOUT;
    }

    int getSearchTimeout() {
        return SEARCH_TIMEOUT;
    }

    int getAnalyticsTimeout() {
        return ANALYTICS_TIMEOUT;
    }

    int getConnectTimeout() {
        return CONNECT_TIMEOUT;
    }

    int getDisconnectTimeout() {
        return DISCONNECT_TIMEOUT;
    }

    int getSocketConnectTimeout() {
        return SOCKET_CONNECT_TIMEOUT;
    }
}