package com.question.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.sys.entity.Questionnaire;
import com.question.modules.sys.mapper.QuestionnaireMapper;
import com.question.modules.sys.service.IQuestionnaireService;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements IQuestionnaireService {
    @Override
    public Integer InsertQuestionnaire(Questionnaire questionnaire) {
        return baseMapper.insert(questionnaire);
    }
}
