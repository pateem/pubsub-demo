package com.example.command;

import com.example.persistence.User;
import com.example.persistence.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PrintAll implements Command {

    private final Logger logger = LogManager.getLogger(PrintAll.class);

    private final UserDao userDao;

    public PrintAll(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute() {
        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            logger.info("No users found");
        } else {
            logger.info("Found {} users:", users.size());
            users.forEach(logger::info);
        }
    }
}
