package com.question.modules.sys.controller;


import com.question.modules.sys.entity.req.LoginMailReq;
import com.question.modules.sys.entity.req.LoginReq;
import com.question.modules.sys.entity.req.RegisterReq;
import com.question.modules.sys.entity.vo.UserVO;
import com.question.modules.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 前端控制器-用户
 *
 * @author 问卷星球团队
 * @since 2021-08-20
 */
@Api(tags = "用户层接口")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("邮箱+密码方式登录")
    @PostMapping("/login")
    public UserVO login(@RequestBody @Validated LoginReq req) {
        return userService.login(req);
    }

    @ApiOperation("验证码方式登录")
    @PostMapping("/mail/login")
    public UserVO loginByMail(@RequestBody @Validated LoginMailReq req) {
        return userService.loginByMail(req);
    }

    @ApiOperation("用户注册")
    @PostMapping("/mail/register")
    public UserVO register(@RequestBody @Validated RegisterReq req) {
        return userService.register(req);
    }


    @ApiOperation("发送邮箱验证码-登录")
    @GetMapping("/mail/send/code/login/{mail}")
    @ApiImplicitParam(name = "mail", value = "邮箱地址")
    public boolean sendMailLoginCoed(@PathVariable String mail) {
        return userService.sendMailLoginCoed(mail);
    }

    @ApiOperation("发送邮箱验证码-注册")
    @GetMapping("/mail/send/code/register/{mail}")
    @ApiImplicitParam(name = "mail", value = "邮箱地址")
    public boolean sendMailRegisterCoed(@PathVariable String mail) {
        return userService.sendMailRegisterCoed(mail);
    }

     // TODO 用户修改昵称和密码
}
