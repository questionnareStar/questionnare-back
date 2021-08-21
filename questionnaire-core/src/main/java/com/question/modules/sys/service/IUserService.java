package com.question.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.sys.entity.User;
import com.question.modules.sys.entity.req.LoginMailReq;
import com.question.modules.sys.entity.req.LoginReq;
import com.question.modules.sys.entity.req.RegisterReq;
import com.question.modules.sys.entity.vo.UserVO;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-20
 */
public interface IUserService extends IService<User> {


    /**
     * 根据账号查询用户
     * @param account 根据账号查询用户
     * @return 返回user或null
     */
    User findUserByAccount(String account);

    /**
     * 发送注册验证码到邮件
     * @param mail 邮箱
     * @return true发送成功
     */
    boolean sendMailRegisterCoed(String mail);

    /**
     * 发送登录验证码到邮件
     * @param mail 邮箱
     * @return true发送成功
     */
    boolean sendMailLoginCoed(String mail);

    /**
     * 用户注册
     * @param req 邮箱、密码、验证码
     * @return  用户信息+token
     */
    UserVO register(RegisterReq req);

    /**
     * 用户登录-验证码方式
     * @param req 邮箱、验证码
     * @return  用户信息+token
     */
    UserVO loginByMail(LoginMailReq req);

    /**
     * 用户登录-账号密码
     * @param req 邮箱、密码
     * @return 用户信息+token
     */
    UserVO login(LoginReq req);
}
