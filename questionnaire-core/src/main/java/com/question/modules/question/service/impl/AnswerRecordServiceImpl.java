package com.question.modules.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.question.entities.AnswerRecord;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.entities.vo.AnswerNumber;
import com.question.modules.question.entities.vo.AnswerNumberVo;
import com.question.modules.question.entities.vo.AnswerRecordVo;
import com.question.modules.question.mapper.AnswerRecordMapper;
import com.question.modules.question.mapper.QuestionnaireMapper;
import com.question.modules.question.service.IAnswerRecordService;
import com.question.modules.sys.entity.User;
import com.question.modules.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    @Autowired
    private UserMapper userMapper;
    @Override
    public AnswerNumberVo createQuestionnaireAnswerNumber(Integer questionnaireId) {
        Questionnaire questionnaire = questionnaireMapper.selectById(questionnaireId);
        QueryWrapper<AnswerRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("questionnaire_id",questionnaireId);
        List<AnswerRecord> answerRecords = answerRecordMapper.selectList(wrapper);
        AnswerNumberVo answerNumberVo = new AnswerNumberVo();
        List<AnswerNumber> answerNumbers = answerNumberVo.createAnswerNumbers();
        List<AnswerRecordVo> answerRecordVos=answerNumberVo.createAnswerRecords();
        Date minDate=answerRecords.get(0).getAnswerTime();
        Date maxDate=answerRecords.get(0).getAnswerTime();
        for (AnswerRecord answerRecord : answerRecords){
            AnswerRecordVo answerRecordVo = new AnswerRecordVo();
            if(answerRecord.getAnswerUsrId()==0){
                answerRecordVo.setUserAccount("--未登录用户--");
            }
            else {
                User user = userMapper.selectById(answerRecord.getAnswerUsrId());
                answerRecordVo.setUserAccount(user.getAccount());
            }
            answerRecordVo.setRecordId(answerRecord.getId());
            answerRecordVo.setAnswerTime(answerRecord.getAnswerTime());
            answerRecordVos.add(answerRecordVo);
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
            if(onlyDate.after(maxDate)){
                maxDate = onlyDate;
            }
            if(onlyDate.before(minDate)){
                minDate = onlyDate;
            }
        }
        long start = minDate.getTime();
        long end = maxDate.getTime();
        if(answerNumbers.size()>0){
            for(long startTimeStamp=start;startTimeStamp<=end;startTimeStamp+=24*3600*1000L){
                // 如果有日期相同的 则合并
                int flag = 0;
                for(AnswerNumber answerNumber:answerNumbers){
                    if(startTimeStamp==answerNumber.getDate().getTime()){
                        flag=1;
                        break;
                    }
                }

                //否则创建一个新的日期
                if(flag==0){
                    AnswerNumber answerNumber = new AnswerNumber();
                    answerNumber.setNumber(0);
                    answerNumber.setDate(new Date(startTimeStamp));
                    answerNumbers.add(answerNumber);
                }
            }
        }
        answerNumbers.sort(new Comparator<AnswerNumber>() {
            @Override
            public int compare(AnswerNumber o1, AnswerNumber o2) {
                if(o1.getDate().after(
                        o2.getDate())){
                    return 1;
                }
                else if(o1.getDate().equals(o2.getDate())){
                    return 0;
                }
                else return -1;
            }
        });
        //问卷
        answerNumberVo.setQuestionnaire(questionnaire);
        return answerNumberVo;
    }
}
