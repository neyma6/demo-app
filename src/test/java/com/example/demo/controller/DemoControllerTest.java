package com.example.demo.controller;

import com.example.demo.controller.configuration.DemoJGivenConfiguration;
import com.example.demo.controller.stage.DemoControllerStage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.JGivenConfiguration;
import com.tngtech.jgiven.integration.spring.SimpleSpringScenarioTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@JGivenConfiguration(DemoJGivenConfiguration.class)
public class DemoControllerTest extends SimpleSpringScenarioTest<DemoControllerStage> {

    //@Test
    @As("The '/hello' endpoint should provide the proper welcome message")
    public void the_hello_endpoint_should_provide_the_proper_welcome_message() throws Exception {
        given().path("/api/v1/demo/hello");
        when().get();
        then().the_status_is(OK).and().the_content_is("Hello World");
    }

    //@Test
    @As("The '/hello' endpoint should provide the proper welcome message with a given name")
    public void the_hello_endpoint_should_provide_the_proper_welcome_message_with_a_given_name() throws Exception {
        given().path("/api/v1/demo/hello/Homer");
        when().get();
        then().the_status_is(OK).and().the_content_is("Hello Homer");
    }

    //@Test
    @As("The path '/foo' should return NOT FOUND")
    public void the_path_foo_returns_not_found() throws Exception {
        given().path("/foo");
        when().get();
        then().the_status_is(NOT_FOUND);
    }
}
