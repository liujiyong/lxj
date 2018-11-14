package com.lxj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

@RestController
@RequestMapping(value = "api/v1/user")
@Api("用户设定相关api")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * 
     * @return
     */
    @ApiOperation(value = "获取用户列表")
    @RequestMapping(method = RequestMethod.GET)
    public BasicResponse<List<User>> getUserList() {
        return userService.getUserList();
    }

    /**
     * 添加用户
     * 
     * @return
     */
    @ApiOperation(value = "添加用户")
    @ApiImplicitParam(name = "user", value = "用户对象,传入json格式", paramType = "body", required = true, dataType = "string")
    @RequestMapping(method = RequestMethod.PUT)
    public BasicResponse<String> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * 删除用户
     * 
     * @return
     */
    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "userid", value = "用户名", paramType = "path", required = true, dataType = "string")
    @RequestMapping(value = "{userid}", method = RequestMethod.DELETE)
    public BasicResponse<String> deleteUser(@PathVariable("userid") String userid) {
        return userService.deleteUserByUserId(userid);
    }

    /**
     * 更新用户
     * 
     * @return
     */
    @ApiOperation(value = "更新用户")
    @ApiImplicitParam(name = "user", value = "用户对象,传入json格式", paramType = "body", required = true, dataType = "string")
    @RequestMapping(method = RequestMethod.POST)
    public BasicResponse<String> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * 修改用户密码
     * 
     * @param user
     * @return
     */
    @ApiOperation(value = "修改用户密码")
    @ApiImplicitParam(name = "user", value = "用户对象,传入json格式<br/>{<br/>userId: 用户名,<br/>oldPassword: 旧密码<br/>, <br/>password: 新密码<br/>}", paramType = "body", required = true, dataType = "string")
    @RequestMapping(value = "changepwd", method = RequestMethod.POST)
    public BasicResponse<String> changePassword(@RequestBody User user) {
        return userService.updateUserPassword(user);
    }
}
