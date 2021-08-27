package com.question.modules.exam.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.question.exception.DefaultException;
import com.question.modules.exam.entities.*;
import com.question.modules.exam.entities.vo.ExamQuestionVo;
import com.question.modules.exam.entities.vo.ExamQuestionnaireDetailVo;
import com.question.modules.exam.entities.vo.statistics.ExamFillInStatistics;
import com.question.modules.exam.entities.vo.statistics.ExamMultiStatistics;
import com.question.modules.exam.entities.vo.statistics.ExamSingleStatistics;
import com.question.modules.exam.entities.vo.statistics.ExamStatisticsVo;
import com.question.modules.exam.mapper.*;
import com.question.modules.exam.service.IExamQuestionBankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.question.entities.FillBlank;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.mapper.QuestionnaireMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Service
public class ExamQuestionBankServiceImpl extends ServiceImpl<ExamQuestionBankMapper, ExamQuestionBank> implements IExamQuestionBankService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private ExamQuestionBankMapper questionBankMapper;

    @Autowired
    private ExamFillInMapper fillInMapper;

    @Autowired
    private ExamSingleMapper singleMapper;

    @Autowired
    private ExamMultiMapper multiMapper;

    @Autowired
    private ExamFillInAnswerMapper fillInAnswerMapper;

    @Autowired
    private ExamSingleAnswerMapper singleAnswerMapper;

    @Autowired
    private ExamMultiAnswerMapper multiAnswerMapper;

    @Override
    public ExamQuestionnaireDetailVo getDetailedQuestionnaire(String code) {
        ExamQuestionnaireDetailVo detailVo = new ExamQuestionnaireDetailVo();
        //查出对应问卷
        QueryWrapper<Questionnaire> wrapper = new QueryWrapper<>();
        wrapper.eq("code",code);
        List<Questionnaire> questionnaires = questionnaireMapper.selectList(wrapper);
        if(questionnaires.size()==0){
            throw new DefaultException("问卷密钥有误！");
        }
        Questionnaire questionnaire = questionnaires.get(0);
        detailVo.setQuestionnaire(questionnaire);
        if(questionnaire.getStamp()!=5){
            throw new DefaultException("该问卷不是考试问卷！");
        }
        //查出所有的题目
        QueryWrapper<ExamQuestionBank> questionBankQueryWrapper = new QueryWrapper<>();
        questionBankQueryWrapper.eq("questionnaire_id",questionnaire.getId());
        List<ExamQuestionBank> questionBanks = questionBankMapper.selectList(questionBankQueryWrapper);
        List<ExamQuestionVo> questionList = new ArrayList<>();
        for(ExamQuestionBank questionBank:questionBanks){
            switch (questionBank.getType()){
                case 1:{
                    //填空题
                    ExamFillIn fillIn = fillInMapper.selectById(questionBank.getQuestionId());
                    ExamQuestionVo questionVo = new ExamQuestionVo();
                    questionVo.setId(fillIn.getId());
                    questionVo.setType(1);
                    questionVo.setQuestion(fillIn.getQuestion());
                    //有答案
                    if(!fillIn.getAnswer().equals("")){
                        questionVo.setAnswer(fillIn.getAnswer());
                    }
                    questionVo.setRequired(fillIn.getRequired());
                    questionVo.setDesc(fillIn.getDesc());
                    questionList.add(questionVo);
                    break;
                }
                case 2:{
                    //多选题
                    ExamMulti multi = multiMapper.selectById(questionBank.getQuestionId());
                    ExamQuestionVo questionVo = new ExamQuestionVo();
                    questionVo.setId(multi.getId());
                    questionVo.setType(2);
                    questionVo.setQuestion(multi.getQuestion());
                    questionVo.setChoices(JSON.parseObject(multi.getChoices(),List.class));
                    //有答案
                    if(!multi.getAnswer().equals("")){
                        questionVo.setAnswers(JSON.parseObject(multi.getAnswer(),List.class));
                    }
                    questionVo.setRequired(multi.getRequired());
                    questionVo.setDesc(multi.getDesc());
                    questionList.add(questionVo);
                    break;
                }
                case 3:{
                    //单选题
                    ExamSingle single = singleMapper.selectById(questionBank.getQuestionId());
                    ExamQuestionVo questionVo = new ExamQuestionVo();
                    questionVo.setId(single.getId());
                    questionVo.setType(3);
                    questionVo.setQuestion(single.getQuestion());
                    questionVo.setChoices(JSON.parseObject(single.getChoices(),List.class));
                    //有答案
                    if(!single.getAnswer().equals("")){
                        questionVo.setAnswer(single.getAnswer());
                    }
                    questionVo.setRequired(single.getRequired());
                    questionVo.setDesc(single.getDesc());
                    questionList.add(questionVo);
                    break;
                }
            }
        }
        detailVo.setQuestionList(questionList);
        return detailVo;
    }

    @Override
    public ExamStatisticsVo getQuestionnaireStatistics(Integer id) {
        //所有的题库表
        QueryWrapper<ExamQuestionBank> questionBankWrapper = new QueryWrapper<>();
        questionBankWrapper.eq("questionnaire_id",id);
        List<ExamQuestionBank> questionBanks = baseMapper.selectList(questionBankWrapper);
        List<Object> questionStatics = new ArrayList<>();
        for(ExamQuestionBank questionBank:questionBanks){
            switch (questionBank.getType()){
                case 1:{
                    //填空题
                    ExamFillIn fillIn = fillInMapper.selectById(questionBank.getQuestionId());
                    QueryWrapper<ExamFillInAnswer> fillInWrapper = new QueryWrapper<>();
                    fillInWrapper.eq("question_id",questionBank.getQuestionId());
                    List<ExamFillInAnswer> fillInAnswers = fillInAnswerMapper.selectList(fillInWrapper);
                    ExamFillInStatistics statistics = new ExamFillInStatistics();
                    List<String> userAnswers = new ArrayList<>();
                    for(ExamFillInAnswer fillInAnswer:fillInAnswers){
                        userAnswers.add(fillInAnswer.getAnswer());
                    }
                    statistics.setQuestion(fillIn.getQuestion());
                    statistics.setType(1);
                    statistics.setUserAnswers(userAnswers);
                    //有答案
                    if(!fillIn.getAnswer().equals("")){
                        statistics.setAnswer(fillIn.getAnswer());
                    }
                    statistics.setAnswerNumbers(fillInAnswers.size());
                    questionStatics.add(statistics);
                }
                case 2:{
                    //多选题
                    ExamMulti multi = multiMapper.selectById(questionBank.getQuestionId());
                    QueryWrapper<ExamMultiAnswer> multiWrapper = new QueryWrapper<>();
                    multiWrapper.eq("question_id",questionBank.getQuestionId());
                    List<ExamMultiAnswer> multiAnswers = multiAnswerMapper.selectList(multiWrapper);
                    ExamMultiStatistics statistics = new ExamMultiStatistics();
                    List<List<String>> userAnswers = new ArrayList<>();
                    List<String> answers = JSON.parseObject(multi.getAnswer(),List.class);
                    int correctNumbers = 0;
                    for(ExamMultiAnswer multiAnswer:multiAnswers){
                        List<String> userAnswer = JSON.parseObject(multiAnswer.getAnswer(),List.class);
                        int allCorrect = 1;
                        for(String answer : answers){
                            int flag =0;
                            for(String userAnswerItem:userAnswer){
                                if(userAnswerItem.equals(answer)){
                                    flag = 1;
                                    break;
                                }
                            }
                            if(flag == 0){
                                allCorrect = 0;
                                break;
                            }
                        }
                       correctNumbers+=allCorrect;
                        userAnswers.add(userAnswer);
                    }
                    statistics.setQuestion(multi.getQuestion());
                    statistics.setType(2);
                    statistics.setUserAnswers(userAnswers);
                    statistics.setAnswerNumbers(multiAnswers.size());
                    //有答案
                    if(!multi.getAnswer().equals("")){
                        statistics.setAnswers(answers);
                        statistics.setCorrectNumbers(correctNumbers);
                    }
                    questionStatics.add(statistics);
                }
                case 3:{
                    //单选题
                    ExamSingle single = singleMapper.selectById(questionBank.getQuestionId());
                    QueryWrapper<ExamSingleAnswer> singleWrapper = new QueryWrapper<>();
                    singleWrapper.eq("question_id",questionBank.getQuestionId());
                    List<ExamSingleAnswer> singleAnswers = singleAnswerMapper.selectList(singleWrapper);
                    ExamSingleStatistics statistics = new ExamSingleStatistics();
                    List<String> userAnswers = new ArrayList<>();
                    int correctNumbers = 0;
                    for(ExamSingleAnswer singleAnswer:singleAnswers){
                        if(singleAnswer.getAnswer().equals(single.getAnswer())){
                            correctNumbers+=1;
                        }
                        userAnswers.add(singleAnswer.getAnswer());
                    }
                    statistics.setQuestion(single.getQuestion());
                    statistics.setType(3);
                    statistics.setUserAnswers(userAnswers);
                    statistics.setAnswerNumbers(singleAnswers.size());
                    //有答案
                    if(!single.getAnswer().equals("")){
                        statistics.setAnswer(single.getAnswer());
                        statistics.setCorrectNumbers(correctNumbers);
                    }
                    questionStatics.add(statistics);
                }
            }
        }
        Questionnaire questionnaire = questionnaireMapper.selectById(id);
        ExamStatisticsVo statisticsVo = new ExamStatisticsVo();
        statisticsVo.setQuestionStatics(questionStatics);
        statisticsVo.setHead(questionnaire.getHead());
        statisticsVo.setIntroduction(questionnaire.getIntroduction());
        statisticsVo.setStartTime(questionnaire.getStartTime());
        statisticsVo.setEndTime(questionnaire.getEndTime());
        statisticsVo.setIsReleased(questionnaire.getIsReleased());
        statisticsVo.setWriteNum(questionnaire.getWriteNum());
        statisticsVo.setCode(questionnaire.getCode());
        statisticsVo.setIsSerial(questionnaire.isSerial());
        return statisticsVo;
    }
}
