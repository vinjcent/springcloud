package com.vinjcent;

import com.vinjcent.pojo.User;
import com.vinjcent.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RedisCacheApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        List<User> users = userService.queryAllUsers();
        System.out.println(users);
        userService.queryAllUsers();
    }

    @Test
    void delete() {
        userService.deleteUserById(1);
    }

}
