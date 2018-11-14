package com.lxj.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lxj.exception.UnauthorizedException;
import com.lxj.mapper.UserMapper;
import com.lxj.model.User;
import com.lxj.response.BasicResponse;
import com.lxj.util.JWTUtil;

@RestController
@RequestMapping(value = "/sample")
public class SampleController {

    private static final Logger LOGGER = LogManager.getLogger(SampleController.class);

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public BasicResponse login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        LOGGER.info("run login()");
        User userBean = userMapper.getUserByUserId(username);
        if (userBean.getPassword().equals(password)) {
            return new BasicResponse(200, "Login success", JWTUtil.sign(username, password));
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/article")
    public BasicResponse article() {
        LOGGER.info("run article()");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new BasicResponse(200, "You are already logged in", null);
        } else {
            return new BasicResponse(200, "You are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public BasicResponse requireAuth() {
        LOGGER.info("run requireAuth()");
        return new BasicResponse(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public BasicResponse requireRole() {
        LOGGER.info("run requireRole()");
        return new BasicResponse(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public BasicResponse requirePermission() {
        LOGGER.info("run requirePermission()");
        return new BasicResponse(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BasicResponse unauthorized() {
        LOGGER.info("run unauthorized()");
        return new BasicResponse(401, "Unauthorized", null);
    }
}
