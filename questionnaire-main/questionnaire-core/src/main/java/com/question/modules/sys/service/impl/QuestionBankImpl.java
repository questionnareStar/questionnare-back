package com.question.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.sys.entity.MultiChoice;
import com.question.modules.sys.entity.QuestionBank;
import com.question.modules.sys.mapper.MultiChoiceMapper;
import com.question.modules.sys.mapper.QuestionBankMapper;
import com.question.modules.sys.service.IQuestionBankService;
import org.springframework.stereotype.Service;

@Service
public class QuestionBankImpl  extends ServiceImpl<QuestionBankMapper, QuestionBank> implements IQuestionBankService {
    @Override
    public Integer InsertQuestionBank(QuestionBank questionBank) {
        return baseMapper.insert(questionBank);
    }
}
