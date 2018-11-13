package com.lxj.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxj.BasicResponse;
import com.lxj.LxjPropertyService;
import com.lxj.exception.UnauthorizedException;
import com.lxj.mapper.UserMapper;
import com.lxj.model.User;
import com.lxj.service.UserService;
import com.lxj.util.IDGenerator;
import com.lxj.util.JWTUtil;
import com.lxj.util.SafeHashGenerator;

@Service("userService")
public class UserServiceImpl implements UserService {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    LxjPropertyService kuaiyiPropertyService;

    public BasicResponse validateUser(String userId, String password) {
        User user = userMapper.getUserByUserId(userId);
        if (user != null && user.getPassword().equals(password)) {
            return new BasicResponse(200, "Login success", JWTUtil.sign(userId, password));
        } else {
            LOGGER.info("userid or password is incorrect.");
            throw new UnauthorizedException();
        }
    }

    public BasicResponse getUserList() {
        List<User> user = userMapper.getAllUsers();
        return new BasicResponse(200, "success", user);
    }

    @Override
    public BasicResponse deleteUserById(String id) {
        int count = userMapper.deleteUserById(id);
        if (count > 0) {
            return new BasicResponse(200, "success", "The user id deleted! ");
        } else {
            return new BasicResponse(200, "fail", null);
        }
    }

    @Override
    public BasicResponse addUser(User user) {

        if (StringUtils.isEmpty(user.getUserId())) {
            return new BasicResponse(200, "fail", "The user id can't null!");
        }

        User oldUser = userMapper.userIsExist(user.getUserId());
        if (oldUser != null) {
            return new BasicResponse(200, "fail", "The user id already exists!");
        }
        user.setId(IDGenerator.generate());
        String initialPassword = RandomStringUtils.randomAlphanumeric(10);
        String hashedInitialPassword = SafeHashGenerator.getStretchedPassword(initialPassword, user.getUserId());
        user.setPassword(hashedInitialPassword);
        user.setCreateTime(new Date());
        int count = userMapper.addUser(user);
        if (count > 0) {
            return new BasicResponse(200, "success", user);
        } else {
            return new BasicResponse(200, "fail", user);
        }

    }

    @Override
    public BasicResponse updateUser(User user) {
        if (StringUtils.isEmpty(user.getUserId())) {
            return new BasicResponse(200, "fail", kuaiyiPropertyService.getValue("201"));
        }

        User oldUser = userMapper.userIsExist(user.getUserId());
        if (oldUser == null) {
            return new BasicResponse(200, "fail", "The user id is deleted!");
        }

        int count = userMapper.updateUserById(user);
        if (count > 0) {
            return new BasicResponse(200, "success", user);
        } else {
            return new BasicResponse(200, "fail", user);
        }
    }

    @Override
    public BasicResponse updateUserPassword(User user) {
        if (StringUtils.isEmpty(user.getUserId())) {
            return new BasicResponse(200, "fail", "The user id can't null!");
        }

        User oldUser = userMapper.userIsExist(user.getUserId());
        if (oldUser == null) {
            return new BasicResponse(200, "fail", "The user id is deleted!");
        }
        String initialPassword = RandomStringUtils.randomAlphanumeric(10);
        String hashedInitialPassword = SafeHashGenerator.getStretchedPassword(initialPassword, user.getUserId());
        user.setPassword(hashedInitialPassword);
        int count = userMapper.updateUserPassword(user);
        if (count > 0) {
            return new BasicResponse(200, "success", user);
        } else {
            return new BasicResponse(200, "fail", user);
        }
    }
}
