package com.example.demo.service;

import com.example.demo.domain.Person;
import com.example.demo.repository.RelationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Gender;
import com.example.demo.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final RelationService relationService;
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;

    @Autowired
    public UserService(RelationService relationService, UserRepository userRepository, RelationRepository relationRepository) {
        this.relationService = relationService;
        this.userRepository = userRepository;
        this.relationRepository = relationRepository;
    }

    public boolean save(Person person, String father, String mother) {
        try {
            boolean success = userRepository.save(new User(person.getName(),
                person.getDateOfBirth(),
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

    public List<Person> getCousins(String name) {
        return convertUserToPerson(userRepository.getCousins(name));
    }

    private List<Person> convertUserToPerson(List<User> users) {
        return users.stream()
            .map(user -> new Person(user.getName(),
                user.getDateOfBirth(), user.getGender().getGender()))
            .collect(Collectors.toList());
    }

    private void saveRelation(String childName, String parentName) {
        relationService.createRelation(childName, parentName);
    }
}
