package com.question.modules.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.question.modules.question.entities.QuestionBank;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题库表 Mapper 接口
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Mapper
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {

}
