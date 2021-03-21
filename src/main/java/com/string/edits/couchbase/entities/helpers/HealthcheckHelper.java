package com.string.edits.couchbase.entities.helpers;

import com.couchbase.client.core.message.internal.PingReport;
import com.couchbase.client.core.message.internal.PingServiceHealth;
import com.couchbase.client.java.Bucket;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class HealthcheckHelper {

    public boolean isAlive(Bucket bucket, int pingTimeout) {
        return areNodesOk(bucket.ping(pingTimeout, TimeUnit.MILLISECONDS));
    }

    private boolean areNodesOk(PingReport pingReport) {
        for(PingServiceHealth pingServiceHealth : pingReport.services()) {
            if(pingServiceHealth.state() != PingServiceHealth.PingState.OK) {
                return false;
            }
        }
        return true;
    }
}
