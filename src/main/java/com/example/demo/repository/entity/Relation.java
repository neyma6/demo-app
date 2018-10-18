package com.example.demo.repository.entity;

import com.arangodb.entity.DocumentField;
import com.example.demo.database.ArangoProperties;

public class Relation {

    @DocumentField(DocumentField.Type.ID)
    private String id;

    @DocumentField(DocumentField.Type.FROM)
    private String parent;

    @DocumentField(DocumentField.Type.TO)
    private String child;

    public Relation(String parent, String child) {
        this.parent = User.class.getSimpleName() + "/" + parent;
        this.child = User.class.getSimpleName() + "/" + child;
    }

    public String getParent() {
        return parent;
    }

    public String getChild() {
        return child;
    }
}
