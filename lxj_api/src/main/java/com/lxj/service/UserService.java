package com.lxj.service;

import com.lxj.BasicResponse;
import com.lxj.model.User;

public interface UserService {

    public BasicResponse validateUser(String userId, String password);

    public BasicResponse getUserList();

    public BasicResponse deleteUserById(String userId);

    public BasicResponse updateUser(User user);

    public BasicResponse addUser(User user);

    public BasicResponse updateUserPassword(User user);
}
