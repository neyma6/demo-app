package com.example.demo.service;

import com.example.demo.database.ArangoProperties;
import com.example.demo.domain.Person;
import com.example.demo.repository.RelationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Gender;
import com.example.demo.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final RelationService relationService;
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final ArangoProperties arangoProperties;

    @Autowired
    public UserService(RelationService relationService, UserRepository userRepository, RelationRepository relationRepository, ArangoProperties arangoProperties) {
        this.relationService = relationService;
        this.userRepository = userRepository;
        this.relationRepository = relationRepository;
        this.arangoProperties = arangoProperties;
    }

    public boolean save(Person person, String father, String mother) {
        try {
            boolean success = userRepository.save(new User(person.getName(),
                convertLocalDateToDate(person),
                Gender.find(person.getGender())));
            if (success && !StringUtils.isEmpty(father)) {
                saveRelation(person.getName(), father);
            }
            if (success && !StringUtils.isEmpty(mother)) {
                saveRelation(person.getName(), mother);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public List<Person> getAll() {
        return convertUserToPerson(userRepository.getAll());
    }

    public List<Person> getByGender(String gender) {
        return convertUserToPerson(userRepository.getByGender(Gender.find(gender)));
    }

    public List<Person> getGrandParent(String name, String gender) {
        return convertUserToPerson(userRepository.getGrandParents(name, Gender.find(gender)));
    }

    private LocalDate convertDateToLocalDate(User user) {
        return user.getDateOfBirth().toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
    }

    private Date convertLocalDateToDate(Person person) {
        return Date.from(person.getDateOfBirth().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private List<Person> convertUserToPerson(List<User> users) {
        return users.stream()
            .map(user -> new Person(user.getName(),
                convertDateToLocalDate(user), user.getGender().getGender()))
            .collect(Collectors.toList());
    }

    private void saveRelation(String childName, String parentName) {
        relationService.createRelation(childName, parentName);
    }
}