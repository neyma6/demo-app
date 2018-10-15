package com.example.demo.repository;

public class Query {

    static final String GET_ALL_USERS = "FOR entity IN @@collection RETURN entity";
    static final String GET_USER_KEY_BY_USER_NAME = "FOR entity IN @@collection FILTER entity.name == @name RETURN entity._key";
    static final String GET_USERS_BY_GENDER = "FOR entity IN @@collection FILTER entity.gender == @gender RETURN entity";
    static final String GET_GRANDPARENTS_BY_GENDER =
        "FOR entity IN @@vertex " +
            "FILTER entity.name == @name " +
            "FOR grandParent IN 2..2 INBOUND entity @@edge " +
            "FILTER grandParent.gender == @gender " +
            "SORT grandParent.dateOfBirth ASC " +
            "RETURN grandParent";
    static final String GET_COUSINS =
        "FOR entity IN @@vertex " +
            "FILTER entity.name == @name " +
            "FOR parent IN 1..1 INBOUND entity @@edge " +
            "LET siblings = (FOR child IN 1..1 OUTBOUND parent @@edge RETURN child) " +
            "FOR grandParent IN 1..1 INBOUND parent @@edge " +
            "FOR cousin IN 2..2 OUTBOUND grandParent @@edge " +
            "FILTER cousin NOT IN siblings " +
            "RETURN cousin";
}
