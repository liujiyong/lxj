package com.lxj.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lxj.LxjPropertyService;
import com.lxj.constants.Messages;
import com.lxj.mapper.UserMapper;
import com.lxj.model.User;
import com.lxj.response.BasicResponse;
import com.lxj.service.UserService;
import com.lxj.util.IDGenerator;
import com.lxj.util.JWTUtil;

@Service("userService")
public class UserServiceImpl implements UserService {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LxjPropertyService lxjProperty;

    @Transactional
    public BasicResponse<String> validateAppUser(String userId, String password) {
        User user = userMapper.getUserByUserId(userId);
        if (user != null) {
            userMapper.updateUserPassword(user);
            return new BasicResponse<String>(200, "Login success", JWTUtil.sign(userId, password));
        } else {
            LOGGER.info("用户手机号不存在。");
            return new BasicResponse<String>(200, "Login fail", String.format(lxjProperty.getValue(Messages.ERR10003), userId));
        }
    }

    public BasicResponse<List<User>> getUserList() {
        List<User> user = userMapper.getAllUsers();
        return new BasicResponse<List<User>>(200, "success", user);
    }

    @Transactional
    public BasicResponse<String> deleteUserByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            LOGGER.info("用户名不能为空。");
            return new BasicResponse<String>(200, "delete fail", String.format(lxjProperty.getValue(Messages.ERR10001), "用户名"));
        }
        
        int count = userMapper.deleteUserByUserId(userId);
        if (count > 0) {
            return new BasicResponse<String>(200, "delete success", lxjProperty.getValue(Messages.MSG99999));
        } else {
            LOGGER.info("用户已被删除。");
            return new BasicResponse<String>(200, "delete fail", lxjProperty.getValue(Messages.ERR99999));
        }
    }

    @Transactional
    public BasicResponse<String> addUser(User user) {
        if (StringUtils.isEmpty(user.getUserId())) {
            LOGGER.info("用户名不能为空。");
            return new BasicResponse<String>(200, "add fail", String.format(lxjProperty.getValue(Messages.ERR10001), "用户名"));
        }

        User oldUser = userMapper.userIsExist(user.getUserId());
        if (oldUser != null) {
            LOGGER.info("用户名已经存在。");
            return new BasicResponse<String>(200, "add fail", String.format(lxjProperty.getValue(Messages.ERR10002), user.getUserId()));
        }
        user.setId(IDGenerator.generate());
//        String hashedInitialPassword = SafeHashGenerator.getStretchedPassword(user.getPassword(), user.getUserId());
//        user.setPassword(hashedInitialPassword);
        user.setPassword(user.getPassword());
        user.setCreateTime(new Date());
        int count = userMapper.addUser(user);
        if (count > 0) {
            return new BasicResponse<String>(200, "add success", lxjProperty.getValue(Messages.MSG99999));
        } else {
            LOGGER.info("添加用户失败。");
            return new BasicResponse<String>(200, "add fail", lxjProperty.getValue(Messages.ERR99999));
        }
    }

    @Transactional
    public BasicResponse<String> updateUser(User user) {
        if (StringUtils.isEmpty(user.getUserId())) {
            LOGGER.info("用户名不能为空。");
            return new BasicResponse<String>(200, "update fail", String.format(lxjProperty.getValue(Messages.ERR10001), "用户名"));
        }

        User oldUser = userMapper.userIsExist(user.getUserId());
        if (oldUser == null) {
            LOGGER.info("用户名不存在。");
            return new BasicResponse<String>(200, "update fail", String.format(lxjProperty.getValue(Messages.ERR10004), user.getUserId()));
        }

        int count = userMapper.updateUserById(user);
        if (count > 0) {
            return new BasicResponse<String>(200, "update success", lxjProperty.getValue(Messages.MSG99999));
        } else {
            LOGGER.info("更新用户失败。");
            return new BasicResponse<String>(200, "update fail", lxjProperty.getValue(Messages.ERR99999));
        }
    }

    @Transactional
    public BasicResponse<String> updateUserPassword(User user) {
        if (StringUtils.isEmpty(user.getUserId())) {
            LOGGER.info("用户名不能为空。");
            return new BasicResponse<String>(200, "password change fail", String.format(lxjProperty.getValue(Messages.ERR10001), "用户名"));
        }

        User oldUser = userMapper.userIsExist(user.getUserId());
        if (oldUser == null) {
            LOGGER.info("用户名不存在。");
            return new BasicResponse<String>(200, "password change fail", String.format(lxjProperty.getValue(Messages.ERR10004), user.getUserId()));
        }
        if (!oldUser.getPassword().equals(user.getOldPassword())) {
            LOGGER.info("旧密码不正确。");
            return new BasicResponse<String>(200, "password change fail", lxjProperty.getValue(Messages.ERR10005));
        }
//        String hashedInitialPassword = SafeHashGenerator.getStretchedPassword(initialPassword, user.getUserId());
//        user.setPassword(hashedInitialPassword);
        oldUser.setPassword(user.getPassword());
        int count = userMapper.updateUserPassword(user);
        if (count > 0) {
            return new BasicResponse<String>(200, "password change success", lxjProperty.getValue(Messages.MSG99999));
        } else {
            return new BasicResponse<String>(200, "password change fail", lxjProperty.getValue(Messages.ERR99999));
        }
    }
}
