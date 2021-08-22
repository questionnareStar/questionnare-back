package com.question.modules.question.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.question.entities.QuestionBank;
import com.question.modules.question.mapper.QuestionBankMapper;
import com.question.modules.question.service.IQuestionBankService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题库表 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements IQuestionBankService {

    @Override
    public List<QuestionBank> findByQuestionId(Integer questionnaireId) {
        QueryWrapper<QuestionBank> wrapper = new QueryWrapper<>();
        wrapper.eq("questionnaire_id",questionnaireId);
        wrapper.orderByAsc("sequence");
        return baseMapper.selectList(wrapper);
    }
}
