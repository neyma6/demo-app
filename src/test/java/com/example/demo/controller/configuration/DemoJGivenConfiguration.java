package com.example.demo.controller.configuration;

import com.tngtech.jgiven.config.AbstractJGivenConfiguration;
import com.tngtech.jgiven.integration.spring.EnableJGiven;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;

@EnableJGiven
@Configuration
public class DemoJGivenConfiguration extends AbstractJGivenConfiguration {

    @Override
    public void configure() {
        setFormatter(HttpStatus.class, (status, annotations) -> format("%s (%d)", status.getReasonPhrase(), status.value()));
    }
}
