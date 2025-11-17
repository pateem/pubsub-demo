package com.example.command;

import com.example.persistence.UserDao;

public class DeleteAll implements Command {

    private final UserDao userDao;

    public DeleteAll(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute() {
        userDao.deleteAll();
    }
}
