package com.lxj.service;

import java.util.List;

import com.lxj.model.User;
import com.lxj.response.BasicResponse;

public interface UserService {

    public BasicResponse<String> validateAppUser(String userId, String password);

    public BasicResponse<List<User>> getUserList();

    public BasicResponse<String> deleteUserByUserId(String userId);

    public BasicResponse<String> updateUser(User user);

    public BasicResponse<String> addUser(User user);

    public BasicResponse<String> updateUserPassword(User user);
}
