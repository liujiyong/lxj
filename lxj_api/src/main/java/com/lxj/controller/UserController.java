package com.lxj.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lxj.BasicResponse;
import com.lxj.model.User;
import com.lxj.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresRoles("manager")
    public BasicResponse getUserList() {
        return userService.getUserList();
    }

    /**
     * 添加用户
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @RequiresRoles("manager")
    public BasicResponse addUser(@RequestBody User user) {

        return userService.addUser(user);
    }

    /**
     * 删除用户
     * 
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @RequiresRoles("manager")
    public BasicResponse deleteUser(@PathVariable("id") String id) {
        return userService.deleteUserById(id);
    }

    /**
     * 更新用户
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @RequiresRoles("manager")
    public BasicResponse updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
