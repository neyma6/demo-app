package com.example.demo.controller.stage;

import com.example.demo.controller.DemoController;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JGivenStage
public class DemoControllerStage extends Stage<DemoControllerStage> {

    @Autowired
    private DemoController demoController;

    private MockMvc mvc;
    private String path;
    private ResultActions mvcResult;

    @BeforeStage
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(demoController).build();
    }

    public DemoControllerStage path(@Quoted String path) {
        this.path = path;
        return this;
    }

    public DemoControllerStage get() throws Exception {
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(path));
        return this;
    }

    public DemoControllerStage the_status_is(HttpStatus status) throws Exception {
        mvcResult.andExpect(status().is(status.value()));
        return this;
    }

    public DemoControllerStage the_content_is(String content) throws Exception {
        mvcResult.andExpect(content().string(content));
        return this;
    }
}
