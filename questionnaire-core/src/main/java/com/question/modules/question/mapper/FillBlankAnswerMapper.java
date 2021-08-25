package com.question.modules.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.question.modules.question.entities.FillBlankAnswer;
import org.apache.ibatis.annotations.Mapper;

/**
 * 填空题回答 Mapper 接口
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Mapper
public interface FillBlankAnswerMapper extends BaseMapper<FillBlankAnswer> {

}
