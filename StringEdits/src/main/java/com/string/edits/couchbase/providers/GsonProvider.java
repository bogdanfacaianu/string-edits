package com.string.edits.couchbase.providers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

@Component
public class GsonProvider {

    @Bean
    public Gson getGson() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
    }
}
