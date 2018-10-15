package com.example.demo.controller;

import com.example.demo.domain.Person;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final UserService userService;

    @Autowired
    public PersonController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public boolean add(Person person, @RequestParam(value = "father", required = false) String father,
                       @RequestParam(value = "mother", required = false) String mother) {
        return userService.save(person, father, mother);
    }

    @GetMapping("/all")
    public List<Person> all() {
        return userService.getAll();
    }

    @GetMapping("/getByGender/{gender}")
    public List<Person> getByGender(@PathVariable("gender") String gender) {
        return userService.getByGender(gender);
    }

    @GetMapping("/grandParents/{gender}")
    public List<Person> getGrandParents(@PathVariable("gender") String gender, @RequestParam("name") String name) {
        return userService.getGrandParent(name, gender);
    }
}
