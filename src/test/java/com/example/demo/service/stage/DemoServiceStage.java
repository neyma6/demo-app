package com.example.demo.service.stage;

import com.example.demo.service.DemoService;
import com.tngtech.jgiven.Stage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DemoServiceStage extends Stage<DemoServiceStage> {

    private DemoService demoService;
    private String message;

    public void a_demo_service() {
        demoService = new DemoService();
    }

    public void get_message_is_called() {
        message = demoService.getHelloMessage();
    }

    public void get_message_is_called_with_$name(String name) {
        message = demoService.getHelloMessage(name);
    }

    public void the_result_equals_to_$message(String expectedMessage) {
        assertThat(message, is(equalTo(expectedMessage)));
    }
}
