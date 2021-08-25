package com.question.modules.question.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.exception.DefaultException;
import com.question.modules.question.entities.*;
import com.question.modules.question.entities.statics.FillBlankStatic;
import com.question.modules.question.entities.statics.MarkStatic;
import com.question.modules.question.entities.statics.MultiChoiceStatic;
import com.question.modules.question.entities.statics.SingleChoiceStatic;
import com.question.modules.question.mapper.*;
import com.question.modules.question.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IFillBlankService fillBlankService;

    @Autowired
    private IMarkService markService;

    @Autowired
    private IMultiChoiceService multiChoiceService;

    @Autowired
    private ISingleChoiceService singleChoiceService;

    @Autowired
    private FillBlankAnswerMapper fillBlankAnswerMapper;

    @Autowired
    private MarkAnswerMapper markAnswerMapper;

    @Autowired
    private MultiChoiceAnswerMapper multiChoiceAnswerMapper;

    @Autowired
    private SingleChoiceAnswerMapper singleChoiceAnswerMapper;

    @Autowired
    private QuestionBankMapper questionBankMapper;

    @Autowired
    private IQuestionnaireService questionnaireService;
    @Override
    public List<QuestionBank> findByQuestionId(Integer questionnaireId) {
        QueryWrapper<QuestionBank> wrapper = new QueryWrapper<>();
        wrapper.eq("questionnaire_id",questionnaireId);
        wrapper.orderByAsc("sequence");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void deleteByQuestionnaireId(Integer id) {
        QueryWrapper<QuestionBank> wrapper = new QueryWrapper<>();
        wrapper.eq("questionnaire_id",id);
        baseMapper.delete(wrapper);
    }

    @Override
    public JSONObject createQuestionnaireStatic(Integer questionnaireId) {
        StringBuilder stringBuilder = new StringBuilder();
        //加入对应的问卷信息
        Questionnaire questionnaire = questionnaireService.getById(questionnaireId);
        Integer userId = StpUtil.getLoginIdAsInt();
        if (!questionnaire.getUserId().equals(userId)) {
            throw new DefaultException("您没有查看该问卷数据的权限");
        }
        stringBuilder.append("{\"write_num\":").append(questionnaire.getWriteNum()).append(",\"end_time\":").append(questionnaire.getEndTime().getTime());
        //对应的题目库
        QueryWrapper<QuestionBank> wrapper = new QueryWrapper<>();
        wrapper.eq("questionnaire_id", questionnaireId);
        List<QuestionBank> questionBankList = baseMapper.selectList(wrapper);
        questionBankList.sort(new Comparator<QuestionBank>() {
            @Override
            public int compare(QuestionBank o1, QuestionBank o2) {
                return o2.getSequence() - o1.getSequence();
            }
        });
        //按顺序加入所有的题目信息
        stringBuilder.append(",\"questions\":[");
        questionBankList.forEach(questionBank -> {
            switch (questionBank.getType()){
                case 1:
                    //1. 找出对应的题目
                    FillBlank fillBlank = fillBlankService.getById(questionBank.getTopicId());
                    //2. 创建改题目的数据对象
                    FillBlankStatic fillBlankStatic = new FillBlankStatic(fillBlank.getQuestion());
                    fillBlankStatic.setTopic(fillBlank.getQuestion());
                    //3. 获取填空题的所有回答
                    QueryWrapper<FillBlankAnswer> fillBlankWrapper = new QueryWrapper<>();
                    fillBlankWrapper.eq("question_id",fillBlank.getId());
                    List<FillBlankAnswer> fillBlankAnswers = fillBlankAnswerMapper.selectList(fillBlankWrapper);
                    for (FillBlankAnswer fillBlankAnswer : fillBlankAnswers) {
                        fillBlankStatic.addAnswer(fillBlankAnswer.getAnswer());
                    }
                    stringBuilder.append(fillBlankStatic).append(",");
                    break;
                case 2:
                    //1. 找出对应的题目
                    Mark mark = markService.getById(questionBank.getTopicId());
                    //2. 创建改题目的数据对象
                    MarkStatic markStatic = new MarkStatic(mark.getQuestion(),mark.getMaxScore());
                    //3. 获取评分的所有回答的数量加入static里
                    for (int i = 0; i <= mark.getMaxScore(); i++) {
                        QueryWrapper<MarkAnswer> markWrapper = new QueryWrapper<>();
                        markWrapper.eq("question_id",mark.getId());
                        markWrapper.eq("answer",i);
                        List<MarkAnswer> markAnswers = markAnswerMapper.selectList(markWrapper);
                        markStatic.addScoreCount(i,markAnswers.size());
                    }
                    stringBuilder.append(markStatic).append(",");
                    break;
                case 3:
                    //1. 找出对应的题目
                    MultiChoice multiChoice = multiChoiceService.getById(questionBank.getTopicId());
                    //2. 创建改题目的数据对象
                    MultiChoiceStatic multiChoiceStatic = new MultiChoiceStatic(multiChoice.getQuestion());
                    //3. 获取所有选项
                    List<String> choices = JSONObject.parseArray(multiChoice.getChoices(),String.class);
                    //4. 创建所有选项，初始数量都是0
                    Map<String,Integer> multiScores = new HashMap<>();
                    for (String choice : choices) {
                        multiScores.put(choice,0);
                    }
                    //5. 获取所有选项的选择次数 加入Static
                    //5.1. 获取所有回答
                    QueryWrapper<MultiChoiceAnswer> multiWrapper = new QueryWrapper<>();
                    multiWrapper.eq("question_id",multiChoice.getId());
                    //5.2 将所有回答统计起来
                    List<MultiChoiceAnswer> multiChoiceAnswers = multiChoiceAnswerMapper.selectList(multiWrapper);
                    for (MultiChoiceAnswer multiChoiceAnswer : multiChoiceAnswers) {
                        List<String> multiAnswers = JSONObject.parseArray(multiChoiceAnswer.getAnswer(),String.class);
                        for (String multiAnswer : multiAnswers) {
                            multiScores.put(multiAnswer, multiScores.get(multiAnswer)+1);
                        }
                    }
                    for(String choice:multiScores.keySet()){
                        multiChoiceStatic.append(choice,multiScores.get(choice));
                    }
                    stringBuilder.append(multiChoiceStatic).append(",");
                    break;
                case 4:
                    //1. 找出对应的题目
                    SingleChoice singleChoice = singleChoiceService.getById(questionBank.getTopicId());
                    //2. 创建改题目的数据对象
                    SingleChoiceStatic singleChoiceStatic = new SingleChoiceStatic(singleChoice.getQuestion());
                    //3. 获取所有选项
                    List<String> singleChoices = JSONObject.parseArray(singleChoice.getChoices(),String.class);
                    //4. 创建所有选项，初始数量都是0
                    QueryWrapper<SingleChoiceAnswer> singleWrapper;
                    for (String choice : singleChoices) {
                        singleWrapper= new QueryWrapper<>();
                        //5. 找到该题的所有回答 统计起来
                        singleWrapper.eq("question_id",singleChoice.getId());
                        singleWrapper.eq("answer",choice);
                        List<SingleChoiceAnswer> singleChoiceAnswers = singleChoiceAnswerMapper.selectList(singleWrapper);
                        singleChoiceStatic.append(choice,singleChoiceAnswers.size());
                    }
                    stringBuilder.append(singleChoiceStatic).append(",");
                    break;
                default:
                    break;
            }
        });
        stringBuilder.append("]}");
        return JSON.parseObject(stringBuilder.toString());
    }
}
