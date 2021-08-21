package com.question.modules.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.question.modules.question.entities.Questionnaire;
import org.apache.ibatis.annotations.Mapper;

/**
 * 问卷表 Mapper 接口
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Mapper
public interface QuestionnaireMapper extends BaseMapper<Questionnaire> {

}
