package com.question.modules.exam.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.exception.DefaultException;
import com.question.modules.exam.entities.*;
import com.question.modules.exam.entities.req.AnswerExamQuestionReq;
import com.question.modules.exam.entities.vo.ExamAnswerRecordVo;
import com.question.modules.exam.entities.vo.ExamQuestionnaireAnswerVo;
import com.question.modules.exam.entities.vo.FeedBackVo;
import com.question.modules.exam.mapper.*;
import com.question.modules.exam.service.IExamAnswerRecordService;
import com.question.modules.exam.service.IExamFillInService;
import com.question.modules.exam.service.IExamMultiService;
import com.question.modules.exam.service.IExamSingleService;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.mapper.QuestionnaireMapper;
import com.question.modules.sys.entity.User;
import com.question.modules.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Service
public class ExamAnswerRecordServiceImpl extends ServiceImpl<ExamAnswerRecordMapper, ExamAnswerRecord> implements IExamAnswerRecordService {

    @Autowired
    private ExamQuestionBankMapper examQuestionBankMapper;

    @Autowired
    private IExamFillInService examFillInService;

    @Autowired
    private IExamMultiService examMultiService;

    @Autowired
    private IExamSingleService examSingleService;

    @Autowired
    private ExamAnswerRecordMapper answerRecordMapper;

    @Autowired
    private ExamFillInAnswerMapper fillInAnswerMapper;

    @Autowired
    private ExamSingleAnswerMapper singleAnswerMapper;

    @Autowired
    private ExamMultiAnswerMapper multiAnswerMapper;

    @Autowired
    private ExamFillInMapper fillInMapper;

    @Autowired
    private ExamSingleMapper singleMapper;

    @Autowired
    private ExamMultiMapper multiMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Override
    public FeedBackVo getAllAnswerRecordByQuestionnaireId(Integer id) {
        QueryWrapper<ExamAnswerRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("questionnaire_id", id);
        List<ExamAnswerRecord> answerRecords = baseMapper.selectList(wrapper);
        List<ExamAnswerRecordVo> answerRecordVos = new ArrayList<>();
        FeedBackVo feedBackVo = new FeedBackVo();
        for (ExamAnswerRecord answerRecord : answerRecords) {
            ExamAnswerRecordVo answerRecordVo = new ExamAnswerRecordVo();
            answerRecordVo.setId(answerRecord.getId());
            answerRecordVo.setAnswerTime(answerRecord.getAnswerTime());
            if (answerRecord.getUserId() == 0) {
                answerRecordVo.setUserAccount("--未登录用户--");
            } else {
                User user = userMapper.selectById(answerRecord.getUserId());
                answerRecordVo.setUserAccount(user.getAccount());
            }
            answerRecordVo.setUserId(answerRecord.getUserId());
            answerRecordVos.add(answerRecordVo);
        }
        feedBackVo.setAnswerRecords(answerRecordVos);
        feedBackVo.setQuestionnaireId(id);
        return feedBackVo;
    }

    public List<ExamAnswerRecord> getExamAnswerRecordByQuestionnaireId(Integer id) {
        QueryWrapper<ExamAnswerRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("questionnaire_id", id);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public ExamQuestionnaireAnswerVo getUserScore(Integer userId, Integer questionnaireId) {
        //获取问卷填写记录
        QueryWrapper<ExamAnswerRecord> answerRecordQueryWrapper = new QueryWrapper<>();
        answerRecordQueryWrapper.eq("user_id", userId);
        answerRecordQueryWrapper.eq("questionnaire_id", questionnaireId);
        ExamAnswerRecord answerRecord = answerRecordMapper.selectOne(answerRecordQueryWrapper);
        //创建问卷填写结果
        ExamQuestionnaireAnswerVo questionnaireAnswerVo = new ExamQuestionnaireAnswerVo();
        Integer totalScore = 0;
        //获取问卷里的所有问题
        QueryWrapper<ExamQuestionBank> wrapper = new QueryWrapper<>();
        wrapper.eq("questionnaire_id", questionnaireId);
        List<ExamQuestionBank> questionBanks = examQuestionBankMapper.selectList(wrapper);
        //创建问题回答结果
        List<ExamQuestionAnswer> questionAnswers = new ArrayList<>();
        for (ExamQuestionBank questionBank : questionBanks) {
            switch (questionBank.getType()) {
                case 1: {
                    ExamFillIn examFillIn = fillInMapper.selectById(questionBank.getQuestionId());
                    ExamQuestionAnswer questionAnswer = new ExamQuestionAnswer();
                    //获取该用户填写的该题答案
                    QueryWrapper<ExamFillInAnswer> fillInAnswerQueryWrapper = new QueryWrapper<>();
                    fillInAnswerQueryWrapper.eq("question_id", examFillIn.getId());
                    fillInAnswerQueryWrapper.eq("record_id", answerRecord.getId());
                    ExamFillInAnswer fillInAnswer = fillInAnswerMapper.selectOne(fillInAnswerQueryWrapper);
                    //用户回答信息
                    questionAnswer.setQuestion(examFillIn.getQuestion());
                    questionAnswer.setReferenceAnswer(examFillIn.getAnswer());
                    questionAnswer.setUserAnswer(fillInAnswer.getAnswer());
                    questionAnswer.setType(1);
                    questionAnswers.add(questionAnswer);
                    break;
                }
                case 2: {
                    ExamMulti examMulti = multiMapper.selectById(questionBank.getQuestionId());
                    ExamQuestionAnswer questionAnswer = new ExamQuestionAnswer();
                    //获取该用户填写的该题答案
                    QueryWrapper<ExamMultiAnswer> multiAnswerQueryWrapper = new QueryWrapper<>();
                    multiAnswerQueryWrapper.eq("question_id", examMulti.getId());
                    multiAnswerQueryWrapper.eq("record_id", answerRecord.getId());
                    ExamMultiAnswer multiAnswer = multiAnswerMapper.selectOne(multiAnswerQueryWrapper);
                    //用户回答信息
                    questionAnswer.setQuestion(examMulti.getQuestion());
                    questionAnswer.setReferenceAnswers(JSON.parseObject(examMulti.getAnswer(), List.class));
                    questionAnswer.setChoices(JSON.parseObject(examMulti.getChoices(), List.class));
                    questionAnswer.setType(2);
                    questionAnswer.setScore(0);
                    //用户未回答
                    if(multiAnswer==null){
                        List<String> userAnswers = new ArrayList<>();
                        userAnswers.add("该用户未回答该题！");
                        questionAnswer.setUserAnswers(userAnswers);
                    }
                    //用户回答了
                    else {
                        questionAnswer.setUserAnswers(JSON.parseObject(multiAnswer.getAnswer(), List.class));
                        List<String> userAnswers = questionAnswer.getUserAnswers();
                        List<String> referenceAnswers = JSON.parseObject(examMulti.getAnswer(), List.class);
                        int correct_number = 0;
                        for (String referenceAnswer : referenceAnswers) {
                            for (String userAnswer : userAnswers) {
                                if (userAnswer.equals(referenceAnswer)) {
                                    correct_number++;
                                    break;
                                }
                            }
                        }
                        if (correct_number == referenceAnswers.size()) {
                            totalScore += examMulti.getScore();
                            questionAnswer.setScore(questionAnswer.getScore() + examMulti.getScore());
                            //没有答案
                            if (examMulti.getAnswer().equals("")) {
                                questionAnswer.setScore(0);
                            }
                        }

                    }
                    questionAnswers.add(questionAnswer);
                    break;
                }
                case 3: {
                    ExamSingle examSingle = singleMapper.selectById(questionBank.getQuestionId());
                    ExamQuestionAnswer questionAnswer = new ExamQuestionAnswer();
                    //获取该用户填写的该题答案
                    QueryWrapper<ExamSingleAnswer> singleAnswerQueryWrapper = new QueryWrapper<>();
                    singleAnswerQueryWrapper.eq("question_id", examSingle.getId());
                    singleAnswerQueryWrapper.eq("record_id", answerRecord.getId());
                    ExamSingleAnswer singleAnswer = singleAnswerMapper.selectOne(singleAnswerQueryWrapper);
                    //用户回答信息
                    questionAnswer.setQuestion(examSingle.getQuestion());
                    questionAnswer.setReferenceAnswer(examSingle.getAnswer());
                    questionAnswer.setChoices(JSON.parseObject(examSingle.getChoices(), List.class));
                    questionAnswer.setType(3);
                    if(singleAnswer==null){
                        questionAnswer.setUserAnswer("该用户未回答该题！");
                        questionAnswer.setScore(0);
                    }
                    else{
                        questionAnswer.setUserAnswer(singleAnswer.getAnswer());
                        if (examSingle.getAnswer().equals(singleAnswer.getAnswer()) && !examSingle.getAnswer().equals("")) {
                            totalScore += examSingle.getScore();
                            questionAnswer.setScore(examSingle.getScore());
                        } else {
                            questionAnswer.setScore(0);
                        }
                    }
                    questionAnswers.add(questionAnswer);
                    break;
                }
            }
        }
        //基本信息
        questionnaireAnswerVo.setUserId(userId);
        questionnaireAnswerVo.setQuestionAnswers(questionAnswers);
        questionnaireAnswerVo.setQuestionnaireId(questionnaireId);
        questionnaireAnswerVo.setTotalScore(totalScore);
        return questionnaireAnswerVo;
    }

    @Override
    public boolean fillIn(String code, List<AnswerExamQuestionReq> reqs) {
        QueryWrapper<Questionnaire> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        List<Questionnaire> questionnaires = questionnaireMapper.selectList(wrapper);
        if (questionnaires.size() == 0) {
            throw new DefaultException("该问卷不存在或已失效！");
        }
        Questionnaire questionnaire = questionnaires.get(0);
        if (questionnaire.getIsReleased() == 0) {
            throw new DefaultException("问卷已关闭！");
        }
        int userId = 0;
        if (StpUtil.isLogin()) {
            userId = StpUtil.getLoginIdAsInt();
        } else {
            throw new DefaultException("请先登录！");
        }
        ExamAnswerRecord answerRecord = new ExamAnswerRecord();
        answerRecord.setAnswerTime(new Date());
        answerRecord.setQuestionnaireId(questionnaire.getId());
        answerRecord.setUserId(userId);
        save(answerRecord);

        //把每一个题的回答存数据库
        for (AnswerExamQuestionReq req : reqs) {
            switch (req.getItemType()) {
                case 1: {
                    ExamFillInAnswer fillInAnswer = new ExamFillInAnswer();
                    fillInAnswer.setAnswer(req.getAnswerList().get(0));
                    fillInAnswer.setRecordId(answerRecord.getId());
                    fillInAnswer.setIsDeleted(false);
                    fillInAnswer.setQuestionId(req.getTopicId());
                    fillInAnswerMapper.insert(fillInAnswer);
                    break;
                }
                case 2: {
                    ExamMultiAnswer multiAnswer = new ExamMultiAnswer();
                    multiAnswer.setAnswer(JSON.toJSONString(req.getAnswerList()));
                    multiAnswer.setRecordId(answerRecord.getId());
                    multiAnswer.setIsDeleted(false);
                    multiAnswer.setQuestionId(req.getTopicId());
                    multiAnswerMapper.insert(multiAnswer);
                    break;
                }
                case 3: {
                    ExamSingleAnswer singleAnswer = new ExamSingleAnswer();
                    singleAnswer.setAnswer(req.getAnswerList().get(0));
                    singleAnswer.setRecordId(answerRecord.getId());
                    singleAnswer.setIsDeleted(false);
                    singleAnswer.setQuestionId(req.getTopicId());
                    singleAnswerMapper.insert(singleAnswer);
                    break;
                }
                default:
                    throw new DefaultException("数据有误");
            }
        }
        questionnaire.setWriteNum(questionnaire.getWriteNum() + 1);
        questionnaireMapper.updateById(questionnaire);
        return true;
    }

}
