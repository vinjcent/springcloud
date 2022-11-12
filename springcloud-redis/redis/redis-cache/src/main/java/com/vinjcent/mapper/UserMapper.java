package com.vinjcent.mapper;

import com.vinjcent.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> queryAllUsers();

    boolean deleteUserById(@Param("id") Integer id);

}
