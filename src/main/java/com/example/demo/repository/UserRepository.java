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

import static com.example.demo.repository.Query.GET_ALL_USERS;
import static com.example.demo.repository.Query.GET_COUSINS;
import static com.example.demo.repository.Query.GET_GRANDPARENTS_BY_GENDER;
import static com.example.demo.repository.Query.GET_USERS_BY_GENDER;
import static com.example.demo.repository.Query.GET_USER_KEY_BY_USER_NAME;

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
            .query(
                GET_ALL_USERS,
                bindVars, null, User.class);
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
            .query(
                GET_USER_KEY_BY_USER_NAME,
                bindVars, null, String.class);

        return userCursor.asListRemaining().stream().findFirst().orElse("");
    }

    public List<User> getByGender(Gender gender) {
        Map<String, Object> bindVars = new MapBuilder()
            .put("@collection", arangoProperties.getVertexCollectionName())
            .put("gender", gender.name())
            .get();

        ArangoCursor<User> userCursor = getArangoProvider().getArangoDatabase()
            .query(
                GET_USERS_BY_GENDER,
                bindVars, null, User.class);
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
            .query(
                GET_GRANDPARENTS_BY_GENDER,
                bindVars, null, User.class);
        return userCursor.asListRemaining();
    }

    public List<User> getCousins(String name) {
        Map<String, Object> bindVars = new MapBuilder()
            .put("@vertex", arangoProperties.getVertexCollectionName())
            .put("@edge", arangoProperties.getEdgeCollectionName())
            .put("name", name)
            .get();

        ArangoCursor<User> userCursor = getArangoProvider().getArangoDatabase()
            .query(
                GET_COUSINS,
                bindVars, null, User.class);
        return userCursor.asListRemaining();
    }
}
