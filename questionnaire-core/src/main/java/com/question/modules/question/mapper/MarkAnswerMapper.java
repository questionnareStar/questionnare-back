package com.question.modules.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.question.modules.question.entities.MarkAnswer;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评分题回答 Mapper 接口
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Mapper
public interface MarkAnswerMapper extends BaseMapper<MarkAnswer> {

}
