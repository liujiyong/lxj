package com.lxj.mapper;

import java.util.List;

import com.lxj.model.User;

public interface UserMapper {

    public User getUserByUserId(String userId);

    public User userIsExist(String userId);

    public List<User> getAllUsers();

    public int deleteUserById(String userId);

    public int addUser(User user);

    public int insertSelective(User user);

    public int updateUserByIdSelective(User user);

    public int updateUserById(User user);

    public int updateUserPassword(User user);

}
