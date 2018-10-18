package com.example.demo.repository;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.entity.CollectionType;
import com.arangodb.model.CollectionCreateOptions;
import com.arangodb.util.MapBuilder;
import com.example.demo.database.ArangoProvider;
import com.example.demo.repository.entity.Gender;
import com.example.demo.repository.entity.Relation;
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

    @Autowired
    public UserRepository(ArangoProvider arangoProvider) {
        super(arangoProvider);
    }

    public List<User> getAll() {

        Map<String, Object> bindVars = new MapBuilder()
            .put("@collection", User.class.getSimpleName())
            .get();
        ArangoCursor<User> userCursor = getArangoProvider().getArangoDatabase()
            .query(
                GET_ALL_USERS,
                bindVars, null, User.class);
        return userCursor.asListRemaining();
    }

    @Override
    public boolean save(User document) {
        return this.save(getArangoProvider().getArangoDatabase().collection(User.class.getSimpleName()), document);
    }

    public String getUserKeyByUserName(String name) {
        Map<String, Object> bindVars = new MapBuilder()
            .put("@collection", User.class.getSimpleName())
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
            .put("@collection", User.class.getSimpleName())
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
            .put("@vertex", User.class.getSimpleName())
            .put("@edge", Relation.class.getSimpleName())
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
            .put("@vertex", User.class.getSimpleName())
            .put("@edge", Relation.class.getSimpleName())
            .put("name", name)
            .get();

        ArangoCursor<User> userCursor = getArangoProvider().getArangoDatabase()
            .query(
                GET_COUSINS,
                bindVars, null, User.class);
        return userCursor.asListRemaining();
    }

    @Override
    public void initRepository() {
        ArangoCollection collection = getArangoProvider().getArangoDatabase().collection(User.class.getSimpleName());
        if (!collection .exists()) {
            getArangoProvider().getArangoDatabase().createCollection(User.class.getSimpleName(),
                new CollectionCreateOptions().type(CollectionType.DOCUMENT));
        }
    }
}
