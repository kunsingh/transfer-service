package com.revolut.exercise;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;


class ApplicationConfig extends ResourceConfig {

    ApplicationConfig() {
        packages("com.revolut.exercise");
        register(JacksonFeature.class);
    }
}
