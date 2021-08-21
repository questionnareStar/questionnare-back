package com.question.modules.sys.controller;


import com.question.modules.sys.entity.req.LoginREQ;
import com.question.modules.sys.entity.req.MailLoginREQ;
import com.question.modules.sys.entity.vo.UserVO;
import com.question.modules.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  前端控制器-用户
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

    @ApiOperation("登录注册一体化")
    @PostMapping("/login")
    public UserVO loginAndRegister(@RequestBody LoginREQ req) {
        return  userService.loginAndRegister(req);
    }

    @ApiOperation("登录注册一体化-邮箱方式")
    @PostMapping("/mail/login")
    public UserVO loginAndRegisterByAll(@RequestBody MailLoginREQ req) {
        // TODO 邮箱方式登录注册未实现
        return  null;
    }

    // TODO 发送邮箱验证码


}
