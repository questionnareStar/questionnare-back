package com.question.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.sys.entity.User;
import com.question.modules.sys.entity.req.LoginREQ;
import com.question.modules.sys.entity.vo.UserVO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-20
 */
public interface IUserService extends IService<User> {

    /**
     * 用户登录/注册，如果有账号则判断密码是否正确，如果没有账号则创建
     * @param req 账号密码
     * @return 用户信息
     */
    UserVO loginAndRegister(LoginREQ req);

    /**
     * 根据账号查询用户
     * @param account 根据账号查询用户
     * @return 返回user或null
     */
    User findUserByAccount(String account);
}
