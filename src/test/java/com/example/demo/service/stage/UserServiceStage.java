package com.example.demo.service.stage;


import com.example.demo.database.ArangoProperties;
import com.example.demo.domain.Person;
import com.example.demo.repository.RelationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Gender;
import com.example.demo.repository.entity.User;
import com.example.demo.service.RelationService;
import com.example.demo.service.UserService;
import com.tngtech.jgiven.Stage;
import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class UserServiceStage extends Stage<UserServiceStage> {

    @Mock
    private RelationService relationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RelationRepository relationRepository;

    private UserService userService;
    private List<Person> response;

    public UserServiceStage init_mocks() {
        MockitoAnnotations.initMocks(this);
        response = null;
        return this;
    }

    public UserServiceStage a_user_service() {
        userService = new UserService(relationService, userRepository, relationRepository);
        return this;
    }

    public UserServiceStage user_repository_stubbed() {
        Mockito.when(userRepository.getAll())
            .thenReturn(Collections.singletonList(new User("", LocalDate.now(), Gender.MALE)));
        return this;
    }

    public UserServiceStage call_get_all() {
        response = userService.getAll();
        return this;
    }

    public UserServiceStage response_is_valid() {
        Assert.assertEquals(1, response.size());
        return this;
    }

    public UserServiceStage user_repository_invoked() {
        Mockito.verify(userRepository, Mockito.only()).getAll();
        return this;
    }
}
