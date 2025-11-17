package com.example.command;

import com.example.persistence.UserDao;

public class UserCommandFactory {

    private final UserDao userDao;

    public UserCommandFactory(UserDao userDao) {
        this.userDao = userDao;
    }

    public Command printAll() {
        return new PrintAll(userDao);
    }

    public Command deleteAll() {
        return new DeleteAll(userDao);
    }

    public Command add(long id, String guid, String name) {
        return new Add(userDao, id, guid, name);
    }
}
