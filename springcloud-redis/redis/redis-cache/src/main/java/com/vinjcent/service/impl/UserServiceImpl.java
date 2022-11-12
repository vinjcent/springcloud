package com.vinjcent.service.impl;

import com.vinjcent.mapper.UserMapper;
import com.vinjcent.pojo.User;
import com.vinjcent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("all")
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)  // 如果当前存在一个事务，则加入该事务；如果当前没有事务，则以非事务方式执行
    public List<User> queryAllUsers() {
        return userMapper.queryAllUsers();
    }
}
