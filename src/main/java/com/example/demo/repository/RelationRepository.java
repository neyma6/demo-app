package com.example.demo.repository;

import com.example.demo.database.ArangoProvider;
import com.example.demo.repository.entity.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RelationRepository extends BaseRepository<Relation> {

    @Autowired
    public RelationRepository(ArangoProvider arangoProvider) {
        super(arangoProvider);
    }

    @Override
    public boolean save(Relation document) {
        return this.save(getArangoProvider().getRelations(), document);
    }
}
