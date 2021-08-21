package com.question.modules.sys.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.exception.DefaultException;
import com.question.modules.sys.entity.User;
import com.question.modules.sys.entity.req.LoginREQ;
import com.question.modules.sys.entity.vo.UserVO;
import com.question.modules.sys.mapper.UserMapper;
import com.question.modules.sys.service.IUserService;
import com.question.properties.RsaProperties;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author 问卷星球团队
 * @since 2021-08-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public UserVO loginAndRegister(LoginREQ req) {
        // 1.查询是否有账号
        User user = findUserByAccount(req.getAccount());

        if (user != null) {
            // 2.如果有则判断密码是否正确，账号状态是否正常
            if (user.getIsDeleted() != 1) {
                throw new DefaultException("你的账号已被冻结，请联系管理员处理");
            }
            // 私钥解密
            String realPassword = SaSecureUtil.rsaDecryptByPrivate(RsaProperties.priKey, user.getPassword());
            if (!realPassword.equals(req.getPassword())) {
                throw new DefaultException("密码错误，请重新输入");
            }
        } else {
            // 3.没有则创建账号
            user = new User();
            // 昵称默认与账号相同
            user.setNickname(req.getAccount());
            user.setAccount(req.getAccount());
            // 密码公钥加密
            user.setPassword(SaSecureUtil.rsaEncryptByPublic(RsaProperties.pubKey, req.getPassword()));
            user.setIsDeleted(1);
            baseMapper.insert(user);
        }
        // 4.登录
        StpUtil.login(user.getId());
        // 5.封装并返回用户信息和Token
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setNickname(user.getNickname());
        userVO.setAccount(user.getAccount());
        userVO.setToken(StpUtil.getTokenValue());
        userVO.setEmail(user.getEmail());
        return userVO;
    }

    @Override
    public User findUserByAccount(String account) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        return baseMapper.selectOne(wrapper);
    }


}
