/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lxj.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lxj.BasicResponse;
import com.lxj.model.User;
import com.lxj.service.UserService;

/**
 * REST Web Service
 *
 * @author admin
 */
@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    /**
     * Creates a new instance of AuthenticationController
     */
    public AuthenticationController() {
    }

    @RequestMapping(value = "logoff", method = RequestMethod.POST)
    public BasicResponse doLogoff() {

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }

        return new BasicResponse(200, "success", "logoff");
    }

    /**
     * 用户登录
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BasicResponse doLogin(@RequestBody User user) {
        LOGGER.info("run login()");
        return userService.validateUser(user.getUserId(), user.getPassword());

    }

    @RequestMapping(value = "changepwd", method = RequestMethod.POST)
    public BasicResponse changePassword(@RequestBody User user) {

        return userService.updateUserPassword(user);
    }

}
