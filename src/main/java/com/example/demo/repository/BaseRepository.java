package com.example.demo.repository;


import com.arangodb.ArangoCollection;
import com.arangodb.entity.DocumentImportEntity;
import com.arangodb.model.DocumentImportOptions;
import com.example.demo.database.ArangoProperties;
import com.example.demo.database.ArangoProvider;

import java.util.Collections;

import static java.util.Objects.nonNull;

public abstract class BaseRepository<T> implements IRepository {

    private final ArangoProvider arangoProvider;

    public BaseRepository(ArangoProvider arangoProvider) {
        this.arangoProvider = arangoProvider;
    }

    public abstract boolean save(T document);

    boolean save(ArangoCollection arangoCollection, T document) {
        DocumentImportOptions importOptions = new DocumentImportOptions();
        importOptions.waitForSync(Boolean.FALSE);
        importOptions.onDuplicate(DocumentImportOptions.OnDuplicate.update);
        importOptions.details(Boolean.TRUE);

        DocumentImportEntity entity = arangoCollection.importDocuments(Collections.singletonList(document), importOptions);

        return isSuccessfulOperation(entity);
    }

    private boolean isSuccessfulOperation(DocumentImportEntity entity) {
        return nonNull(entity) && entity.getErrors().compareTo(0) == 0;
    }

    public ArangoProperties getArangoProperties() {
        return arangoProvider.getArangoProperties();
    }

    ArangoProvider getArangoProvider() {
        return arangoProvider;
    }
}
