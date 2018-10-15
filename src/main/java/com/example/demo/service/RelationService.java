package com.example.demo.service;

import com.example.demo.database.ArangoProperties;
import com.example.demo.repository.RelationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationService {

    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final ArangoProperties arangoProperties;

    @Autowired
    public RelationService(UserRepository userRepository, RelationRepository relationRepository, ArangoProperties arangoProperties) {
        this.userRepository = userRepository;
        this.relationRepository = relationRepository;
        this.arangoProperties = arangoProperties;
    }

    public void createRelation(String childName, String parentName) {
        String childId = userRepository.getUserKeyByUserName(childName);
        String parentId = userRepository.getUserKeyByUserName(parentName);
        Relation relation = new Relation(parentId, childId, arangoProperties);
        relationRepository.save(relation);
    }
}
