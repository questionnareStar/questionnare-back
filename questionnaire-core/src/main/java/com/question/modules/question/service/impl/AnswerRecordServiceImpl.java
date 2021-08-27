package com.question.modules.question.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.question.entities.*;
import com.question.modules.question.entities.vo.AnswerNumber;
import com.question.modules.question.entities.vo.AnswerNumberVo;
import com.question.modules.question.entities.vo.AnswerRecordVo;
import com.question.modules.question.entities.vo.QuestionnaireAnswerVo;
import com.question.modules.question.mapper.*;
import com.question.modules.question.service.IAnswerRecordService;
import com.question.modules.sys.entity.User;
import com.question.modules.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private QuestionBankMapper questionBankMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FillBlankAnswerMapper fillBlankAnswerMapper;
    @Autowired
    private MarkAnswerMapper markAnswerMapper;
    @Autowired
    private MultiChoiceAnswerMapper multiChoiceAnswerMapper;
    @Autowired
    private SingleChoiceAnswerMapper singleChoiceAnswerMapper;
    @Autowired
    private FillBlankMapper fillBlankMapper;
    @Autowired
    private MarkMapper markMapper;
    @Autowired
    private MultiChoiceMapper multiChoiceMapper;
    @Autowired
    private SingleChoiceMapper singleChoiceMapper;
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

    @Override
    public QuestionnaireAnswerVo getAnswerRecordDetailInfo(Integer questionnaireId, Integer record_id) {
        QuestionnaireAnswerVo questionnaireAnswer = new QuestionnaireAnswerVo();
        AnswerRecord answerRecord = answerRecordMapper.selectById(record_id);
        User user = userMapper.selectById(answerRecord.getAnswerUsrId());
        questionnaireAnswer.setQuestionnaireId(questionnaireId);
        questionnaireAnswer.setAccount(user.getAccount());
        questionnaireAnswer.setUserId(user.getId());
        List<QuestionAnswer> questionAnswers = new ArrayList<>();
        QueryWrapper<QuestionBank> questionBankQueryWrapper = new QueryWrapper<>();
        questionBankQueryWrapper.eq("questionnaire_id",questionnaireId);
        List<QuestionBank> questionBanks = questionBankMapper.selectList(questionBankQueryWrapper);
        for(QuestionBank questionBank:questionBanks){
            switch (questionBank.getType()){
                case 1:
                    //填空题
                    FillBlank fillBlank = fillBlankMapper.selectById(questionBank.getTopicId());
                    QueryWrapper<FillBlankAnswer> fillBlankWrapper = new QueryWrapper<>();
                    fillBlankWrapper.eq("question_id",questionBank.getTopicId());
                    fillBlankWrapper.eq("record_id",record_id);
                    FillBlankAnswer fillBlankAnswer = fillBlankAnswerMapper.selectList(fillBlankWrapper).get(0);
                    QuestionAnswer questionAnswer1 = new QuestionAnswer();
                    questionAnswer1.setQuestion(fillBlank.getQuestion());
                    questionAnswer1.setUserAnswer(fillBlankAnswer.getAnswer());
                    questionAnswer1.setType(1);
                    questionAnswers.add(questionAnswer1);
                    break;
                case 2:
                    //评分题
                    Mark mark = markMapper.selectById(questionBank.getTopicId());
                    QueryWrapper<MarkAnswer> markWrapper = new QueryWrapper<>();
                    markWrapper.eq("question_id",questionBank.getTopicId());
                    markWrapper.eq("record_id",record_id);
                    MarkAnswer markAnswer = markAnswerMapper.selectList(markWrapper).get(0);
                    QuestionAnswer questionAnswer2 = new QuestionAnswer();
                    questionAnswer2.setQuestion(mark.getQuestion());
                    questionAnswer2.setScore(markAnswer.getAnswer());
                    questionAnswer2.setType(2);
                    questionAnswer2.setMaxScore(mark.getMaxScore());
                    questionAnswers.add(questionAnswer2);
                    break;
                case 3:
                    //多选题
                    MultiChoice multiChoice = multiChoiceMapper.selectById(questionBank.getTopicId());
                    QueryWrapper<MultiChoiceAnswer> multiWrapper = new QueryWrapper<>();
                    multiWrapper.eq("question_id",questionBank.getTopicId());
                    multiWrapper.eq("record_id",record_id);
                    MultiChoiceAnswer multiChoiceAnswer = multiChoiceAnswerMapper.selectList(multiWrapper).get(0);
                    QuestionAnswer questionAnswer3 = new QuestionAnswer();
                    questionAnswer3.setQuestion(multiChoice.getQuestion());
                    questionAnswer3.setUserAnswers(JSON.parseObject(multiChoiceAnswer.getAnswer(),List.class));
                    questionAnswer3.setChoices(JSON.parseObject(multiChoice.getChoices(),List.class));
                    questionAnswer3.setType(3);
                    questionAnswers.add(questionAnswer3);
                    break;
                case 4:
                    //单选题
                    SingleChoice singleChoice = singleChoiceMapper.selectById(questionBank.getTopicId());
                    QueryWrapper<SingleChoiceAnswer> singleMapper = new QueryWrapper<>();
                    singleMapper.eq("question_id",questionBank.getTopicId());
                    singleMapper.eq("record_id",record_id);
                    SingleChoiceAnswer singleChoiceAnswer = singleChoiceAnswerMapper.selectList(singleMapper).get(0);
                    QuestionAnswer questionAnswer4 = new QuestionAnswer();
                    questionAnswer4.setQuestion(singleChoice.getQuestion());
                    questionAnswer4.setUserAnswer(singleChoiceAnswer.getAnswer());
                    questionAnswer4.setChoices(JSON.parseObject(singleChoice.getChoices(),List.class));
                    questionAnswer4.setType(4);
                    questionAnswers.add(questionAnswer4);
                    break;
            }
        }
        questionnaireAnswer.setQuestionAnswers(questionAnswers);
        return questionnaireAnswer;
    }
}
