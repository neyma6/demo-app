package com.example.demo.service;

import com.example.demo.service.stage.DemoServiceStage;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import org.junit.Test;

public class DemoServiceTest extends SimpleScenarioTest<DemoServiceStage> {

    @Test
    public void should_provide_proper_message() {
        given().a_demo_service();
        when().get_message_is_called();
        then().the_result_equals_to_$message("Hello World");
    }

    @Test
    public void should_provide_proper_message_with_a_given_name() {
        given().a_demo_service();
        when().get_message_is_called_with_$name("Homer");
        then().the_result_equals_to_$message("Hello Homer");
    }
}
