package com.example.persistence;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    void save(User user);

    void deleteAll();

}
