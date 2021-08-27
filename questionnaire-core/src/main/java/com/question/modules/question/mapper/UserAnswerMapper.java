package com.question.modules.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.question.modules.question.entities.UserAnswer;
import org.apache.ibatis.annotations.Mapper;

/**
 * 记录用户填写的信息 Mapper 接口
 *
 * @author 问卷星球团队
 * @since 2021-08-27
 */
@Mapper
public interface UserAnswerMapper extends BaseMapper<UserAnswer> {

}
