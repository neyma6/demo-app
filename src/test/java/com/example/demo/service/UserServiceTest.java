package com.example.demo.service;

import com.example.demo.service.stage.UserServiceStage;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import org.junit.Test;

public class UserServiceTest extends SimpleScenarioTest<UserServiceStage> {

    @Test
    public void get_all_should_provide_every_person() {
        given()
            .init_mocks()
            .user_repository_stubbed()
            .a_user_service();

        when().call_get_all();

        given()
            .response_is_valid()
            .user_repository_invoked();
    }
}
