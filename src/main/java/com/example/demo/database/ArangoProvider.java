package com.example.demo.database;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.CollectionType;
import com.arangodb.entity.EdgeDefinition;
import com.arangodb.model.CollectionCreateOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class ArangoProvider {
    private final ArangoDB arangoDB;
    private final ArangoProperties arangoProperties;

    @Autowired
    public ArangoProvider(ArangoDB arangoDB, ArangoProperties arangoProperties) {
        this.arangoDB = arangoDB;
        this.arangoProperties = arangoProperties;
    }

    public ArangoDatabase getArangoDatabase() {
        return arangoDB.db(arangoProperties.getDataBaseName());
    }

    public ArangoCollection getUserCollection() {
        return arangoDB.db(arangoProperties.getDataBaseName()).collection(arangoProperties.getVertexCollectionName());
    }

    public ArangoCollection getRelations() {
        return arangoDB.db(arangoProperties.getDataBaseName()).collection(arangoProperties.getEdgeCollectionName());
    }

    @PostConstruct
    public void init() {
        ArangoDatabase database = arangoDB.db(arangoProperties.getDataBaseName());
        if (!database.exists()) {
            arangoDB.createDatabase(arangoProperties.getDataBaseName());
            database = arangoDB.db(arangoProperties.getDataBaseName());
            database.createCollection(arangoProperties.getVertexCollectionName(), new CollectionCreateOptions().type(CollectionType.DOCUMENT));
            database.createCollection(arangoProperties.getEdgeCollectionName(), new CollectionCreateOptions().type(CollectionType.EDGES));

            database.createGraph(arangoProperties.getGraphName(), Collections.singletonList(new EdgeDefinition()
                .collection(arangoProperties.getEdgeCollectionName())
                .from(arangoProperties.getVertexCollectionName())
                .to(arangoProperties.getVertexCollectionName())));
        }
    }
}
