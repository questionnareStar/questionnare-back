package com.questionnaire.modules.question.service.impl;

import com.questionnaire.entities.User;
import com.questionnaire.modules.question.mapper.UserMapper;
import com.questionnaire.modules.question.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
