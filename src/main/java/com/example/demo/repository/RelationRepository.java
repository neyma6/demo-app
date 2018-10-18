package com.example.demo.repository;

import com.arangodb.ArangoCollection;
import com.arangodb.entity.CollectionType;
import com.arangodb.model.CollectionCreateOptions;
import com.example.demo.database.ArangoProperties;
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
        return this.save(getArangoProvider().getArangoDatabase().collection(Relation.class.getSimpleName()), document);
    }

    @Override
    public void initRepository() {
        ArangoCollection collection = getArangoProvider().getArangoDatabase().collection(Relation.class.getSimpleName());
        if (!collection .exists()) {
            getArangoProvider().getArangoDatabase().createCollection(Relation.class.getSimpleName(),
                new CollectionCreateOptions().type(CollectionType.EDGES));
        }
    }
}
