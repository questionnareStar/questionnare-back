package com.question.modules.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.question.modules.question.entities.SingleChoice;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 单选题 Mapper 接口
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Mapper
public interface SingleChoiceMapper extends BaseMapper<SingleChoice> {

}
