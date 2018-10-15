package com.example.demo.repository;

import com.arangodb.ArangoCursor;
import com.arangodb.util.MapBuilder;
import com.example.demo.database.ArangoProperties;
import com.example.demo.database.ArangoProvider;
import com.example.demo.repository.entity.Gender;
import com.example.demo.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepository extends BaseRepository<User> {

    private final ArangoProperties arangoProperties;

    @Autowired
    public UserRepository(ArangoProvider arangoProvider, ArangoProperties arangoProperties) {
        super(arangoProvider);
        this.arangoProperties = arangoProperties;
    }

    public List<User> getAll() {

        Map<String, Object> bindVars = new MapBuilder()
            .put("@collection", arangoProperties.getVertexCollectionName())
            .get();
        ArangoCursor<User> userCursor = getArangoProvider().getArangoDatabase()
            .query("FOR entity IN @@collection RETURN entity", bindVars, null, User.class);
        return userCursor.asListRemaining();
    }

    @Override
    public boolean save(User document) {
        return this.save(getArangoProvider().getUserCollection(), document);
    }

    public String getUserKeyByUserName(String name) {
        Map<String, Object> bindVars = new MapBuilder()
            .put("@collection", arangoProperties.getVertexCollectionName())
            .put("name", name)
            .get();

        ArangoCursor<String> userCursor = getArangoProvider().getArangoDatabase()
            .query("FOR entity IN @@collection FILTER entity.name == @name RETURN entity._key", bindVars,  null, String.class);

        return userCursor.asListRemaining().stream().findFirst().orElse("");
    }

    public List<User> getByGender(Gender gender) {
        Map<String, Object> bindVars = new MapBuilder()
            .put("@collection", arangoProperties.getVertexCollectionName())
            .put("gender", gender.name())
            .get();

        ArangoCursor<User> userCursor = getArangoProvider().getArangoDatabase()
            .query("FOR entity IN @@collection FILTER entity.gender == @gender RETURN entity", bindVars,  null, User.class);
        return userCursor.asListRemaining();
    }

    public List<User> getGrandParents(String name, Gender gender) {

        Map<String, Object> bindVars = new MapBuilder()
            .put("@vertex", arangoProperties.getVertexCollectionName())
            .put("@edge", arangoProperties.getEdgeCollectionName())
            .put("gender", gender.name())
            .put("name", name)
            .get();

        ArangoCursor<User> userCursor = getArangoProvider().getArangoDatabase()
            .query("FOR entity IN @@vertex " +
                "FILTER entity.name == @name " +
                "FOR grandParent IN 2..2 INBOUND entity @@edge " +
                "FILTER grandParent.gender == @gender " +
                "SORT grandParent.dateOfBirth ASC " +
                "RETURN grandParent", bindVars,  null, User.class);
        return userCursor.asListRemaining();
    }

    public List<User> getCousins(String name) {
        Map<String, Object> bindVars = new MapBuilder()
            .put("@vertex", arangoProperties.getVertexCollectionName())
            .put("@edge", arangoProperties.getEdgeCollectionName())
            .put("name", name)
            .get();

        ArangoCursor<User> userCursor = getArangoProvider().getArangoDatabase()
            .query("FOR entity IN @@vertex " +
                "FILTER entity.name == @name " +
                "FOR parent IN 1..1 INBOUND entity @@edge " +
                "LET siblings = (FOR child IN 1..1 OUTBOUND parent @@edge RETURN child) " +
                "FOR grandParent IN 1..1 INBOUND parent @@edge " +
                "FOR cousin IN 2..2 OUTBOUND grandParent @@edge " +
                "FILTER cousin NOT IN siblings " +
                "RETURN cousin", bindVars,  null, User.class);
        return userCursor.asListRemaining();
    }
}
