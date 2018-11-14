/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lxj.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lxj.model.User;
import com.lxj.response.BasicResponse;
import com.lxj.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * REST Web Service
 *
 * @author admin
 */
@RestController
@RequestMapping(value = "/authentication")
@Api("授权和系统设定相关api")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    /**
     * 用户登出
     * 
     * @param user
     * @return
     */
    @ApiOperation(value = "用户退出登录")
    @RequestMapping(value = "logoff", method = RequestMethod.POST)
    public BasicResponse<String> doLogoff() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }
        return new BasicResponse<String>(200, "success", "logoff");
    }

    /**
     * 用户绑定手机号
     * 
     * @param user
     * @return
     */
    @ApiOperation(value = "用户绑定手机号并发行授权jwt")
    @ApiImplicitParam(name = "user", value = "用户对象,传入json格式<br/>{<br/>userId: 用户手机号,<br/>password: 手机唯一识别码<br/>}", paramType = "body", required = true, dataType = "string")
    @RequestMapping(value = "applogin", method = RequestMethod.POST)
    public BasicResponse<String> doAppLogin(@RequestBody User user) {
        return userService.validateAppUser(user.getUserId(), user.getPassword());
    }
}
