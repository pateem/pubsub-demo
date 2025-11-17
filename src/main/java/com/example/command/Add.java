package com.example.command;

import com.example.persistence.User;
import com.example.persistence.UserDao;

public class Add implements Command {


  private final UserDao userDao;

  private final User user;

  public Add(UserDao userDao, long id, String guid, String name) {
    this.userDao = userDao;
    this.user = new User(id, guid, name);
  }

  @Override
  public void execute() {
    userDao.save(user);
  }
}
