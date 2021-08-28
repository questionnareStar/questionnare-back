package com.question.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.question.modules.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper 接口
 *
 * @author 问卷星球团队
 * @since 2021-08-20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
