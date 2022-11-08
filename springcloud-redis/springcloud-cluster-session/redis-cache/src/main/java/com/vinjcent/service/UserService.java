package com.vinjcent.service;

import com.vinjcent.pojo.User;

import java.util.List;

public interface UserService {

    boolean deleteUserById(Integer id);

    List<User> queryAllUsers();

}
