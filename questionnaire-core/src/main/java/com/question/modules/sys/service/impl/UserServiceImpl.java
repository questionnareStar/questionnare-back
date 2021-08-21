package com.question.modules.sys.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.data.CodeData;
import com.question.exception.DefaultException;
import com.question.modules.sys.entity.User;
import com.question.modules.sys.entity.req.LoginMailReq;
import com.question.modules.sys.entity.req.LoginReq;
import com.question.modules.sys.entity.req.RegisterReq;
import com.question.modules.sys.entity.vo.UserVO;
import com.question.modules.sys.mapper.UserMapper;
import com.question.modules.sys.service.IUserService;
import com.question.properties.RsaProperties;
import com.question.utils.MailUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 服务实现类
 *
 * @author 问卷星球团队
 * @since 2021-08-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User findUserByAccount(String account) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean sendMailRegisterCoed(String mail) {
        User user = findUserByAccount(mail);
        if (user != null) {
            throw new DefaultException("您的邮箱已注册，请直接登录");
        }
        String code = String.valueOf(makeAuthCode());
        // 添加到数据中心
        CodeData.mailRegisterCode.put(mail, code);
        // 发送验证码
        MailUtil.sendCode(mail, code);
        return true;
    }

    @Override
    public boolean sendMailLoginCoed(String mail) {
        User user = findUserByAccount(mail);
        if (user == null) {
            throw new DefaultException("您尚未注册，请先注册");
        }
        String code = String.valueOf(makeAuthCode());
        // 添加到数据中心
        CodeData.mailLoginCode.put(mail, code);
        // 发送验证码
        MailUtil.sendCode(mail, code);
        return true;
    }

    @Override
    public UserVO register(RegisterReq req) {
        User u = findUserByAccount(req.getAccount());
        if (u != null) {
            throw new DefaultException("您的账号已注册");
        }
        String realCode = CodeData.mailRegisterCode.get(req.getAccount());
        if (StringUtils.isEmpty(realCode)) {
            throw new DefaultException("请先发送验证码");
        }
        if (!realCode.equals(req.getCode())){
            throw new DefaultException("验证码错误");
        }
        // 删除验证码
        CodeData.mailRegisterCode.remove(req.getAccount());
        User user = new User();
        user.setNickname(req.getAccount());
        user.setAccount(req.getAccount());
        // 密码公钥加密
        user.setPassword(SaSecureUtil.rsaEncryptByPublic(RsaProperties.pubKey, req.getPassword()));
        user.setIsDeleted(1);
        baseMapper.insert(user);

        // 登录
        StpUtil.login(user.getId());

        // 封装返回信息
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setNickname(user.getNickname());
        vo.setAccount(user.getAccount());
        vo.setToken(StpUtil.getTokenValue());
        return vo;
    }

    @Override
    public UserVO loginByMail(LoginMailReq req) {
        User user = findUserByAccount(req.getAccount());
        String realCode = CodeData.mailLoginCode.get(req.getAccount());
        if (StringUtils.isEmpty(realCode)) {
            throw new DefaultException("请先发送验证码");
        }
        if (!realCode.equals(req.getCode())){
            throw new DefaultException("验证码错误");
        }
        if (user.getIsDeleted()!=1) {
            throw new DefaultException("你的账号已被冻结，请联系管理员");
        }
        // 登录
        StpUtil.login(user.getId());

        // 封装返回信息
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setNickname(user.getNickname());
        vo.setAccount(user.getAccount());
        vo.setToken(StpUtil.getTokenValue());
        return vo;
    }

    @Override
    public UserVO login(LoginReq req) {
        User user = findUserByAccount(req.getAccount());
        if (user==null){
            throw new DefaultException("未查询到用户");
        }
        // 2.如果有则判断密码是否正确，账号状态是否正常
        if (user.getIsDeleted() != 1) {
            throw new DefaultException("你的账号已被冻结，请联系管理员处理");
        }
        // 私钥解密
        String realPassword = SaSecureUtil.rsaDecryptByPrivate(RsaProperties.priKey, user.getPassword());
        if (!realPassword.equals(req.getPassword())) {
            throw new DefaultException("密码错误，请重新输入");
        }
        if (user.getIsDeleted()!=1) {
            throw new DefaultException("你的账号已被冻结，请联系管理员");
        }
        // 登录
        StpUtil.login(user.getId());

        // 封装返回信息
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setNickname(user.getNickname());
        vo.setAccount(user.getAccount());
        vo.setToken(StpUtil.getTokenValue());
        return vo;
    }

    /**
     * 获取4为数验证码，纯数字
     *
     * @return 验证码
     */
    public int makeAuthCode() {
        return (int) Math.round(Math.random() * (9999 - 1000) + 1000);
    }

}
