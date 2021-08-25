package com.question.modules.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.question.entities.AnswerRecord;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.entities.vo.AnswerNumber;
import com.question.modules.question.entities.vo.AnswerNumberVo;
import com.question.modules.question.mapper.AnswerRecordMapper;
import com.question.modules.question.mapper.QuestionnaireMapper;
import com.question.modules.question.service.IAnswerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-25
 */
@Service
public class AnswerRecordServiceImpl extends ServiceImpl<AnswerRecordMapper, AnswerRecord> implements IAnswerRecordService {

    @Autowired
    private AnswerRecordMapper answerRecordMapper;
    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    @Override
    public AnswerNumberVo createQuestionnaireAnswerNumber(Integer questionnaireId) {
        Questionnaire questionnaire = questionnaireMapper.selectById(questionnaireId);
        QueryWrapper<AnswerRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("questionnaire_id",questionnaireId);
        List<AnswerRecord> answerRecords = answerRecordMapper.selectList(wrapper);
        AnswerNumberVo answerNumberVo = new AnswerNumberVo();
        List<AnswerNumber> answerNumbers = answerNumberVo.createAnswerNumbers();
        for (AnswerRecord answerRecord : answerRecords){
            int flag = 0;
            Date onlyDate = new Date(answerRecord.getAnswerTime().getTime() - (answerRecord.getAnswerTime().getTime()+8*3600000)%86400000);

            // 如果有日期相同的 则合并
            for(AnswerNumber answerNumber:answerNumbers){
                if(onlyDate.equals(answerNumber.getDate())){
                    answerNumber.setNumber(answerNumber.getNumber()+1);
                    flag=1;
                    break;
                }
            }

            //否则创建一个新的日期
            if(flag==0){
                AnswerNumber answerNumber = new AnswerNumber();
                answerNumber.setNumber(1);
                answerNumber.setDate(onlyDate);
                answerNumbers.add(answerNumber);
            }
        }
        answerNumberVo.setQuestionnaire(questionnaire);
        return answerNumberVo;
    }
}
