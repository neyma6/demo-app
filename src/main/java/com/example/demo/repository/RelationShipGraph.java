package com.example.demo.repository;

import com.arangodb.ArangoGraph;
import com.arangodb.entity.EdgeDefinition;
import com.example.demo.database.ArangoProvider;
import com.example.demo.repository.entity.Relation;
import com.example.demo.repository.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class RelationShipGraph implements IGraph {

    private final ArangoProvider arangoProvider;

    public RelationShipGraph(ArangoProvider arangoProvider) {
        this.arangoProvider = arangoProvider;
    }

    @Override
    public void initGraph() {
        ArangoGraph arangoGraph = arangoProvider.getArangoDatabase().graph(this.getClass().getSimpleName());

        if (!arangoGraph.exists()) {
            arangoProvider.getArangoDatabase().createGraph(this.getClass().getSimpleName(), Collections.singletonList(new EdgeDefinition()
                .collection(Relation.class.getSimpleName())
                .from(User.class.getSimpleName())
                .to(User.class.getSimpleName())));
        }
    }
}
