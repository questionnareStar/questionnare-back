package com.question.modules.question.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.exception.DefaultException;
import com.question.modules.question.entities.FillBlank;
import com.question.modules.question.entities.Mark;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.entities.UserAnswer;
import com.question.modules.question.entities.apply.vo.ApplyChoiceVo;
import com.question.modules.question.entities.req.FillInQuestionnaireReq;
import com.question.modules.question.entities.vo.AnswerUserVo;
import com.question.modules.question.entities.vo.MultiChoiceVo;
import com.question.modules.question.entities.vo.SingleChoiceVo;
import com.question.modules.question.mapper.ApplyOptionsMapper;
import com.question.modules.question.mapper.QuestionnaireMapper;
import com.question.modules.question.mapper.UserAnswerMapper;
import com.question.modules.question.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录用户填写的信息 服务实现类
 *
 * @author 问卷星球团队
 * @since 2021-08-27
 */
@Service
public class UserAnswerServiceImpl extends ServiceImpl<UserAnswerMapper, UserAnswer> implements IUserAnswerService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    /**
     * 填空题
     */
    @Autowired
    private IFillBlankService fillBlankService;

    /**
     * 评分题
     */
    @Autowired
    private IMarkService markService;

    /**
     * 多选题
     */
    @Autowired
    private IMultiChoiceService multiChoiceService;

    /**
     * 单选题
     */
    @Autowired
    private ISingleChoiceService singleChoiceService;


    /**
     * 选项
     */
    @Autowired
    private ApplyOptionsMapper applyOptionsMapper;

    @Override
    public List<List<AnswerUserVo>> findQuestionAnswer(String code) {
        Questionnaire questionnaire = getQuestionnaireByCode(code);
        QueryWrapper<UserAnswer> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionnaire.getId());
        List<UserAnswer> userAnswerList = baseMapper.selectList(wrapper);

        List<List<AnswerUserVo>> voList = new ArrayList<>();

        // 遍历所有用户所填写的
        userAnswerList.forEach(userAnswer -> {
            List<FillInQuestionnaireReq> stringList = JSON.parseObject(userAnswer.getAnswer(), List.class);
            List<AnswerUserVo> answerVoList = new ArrayList<>();

            List<FillInQuestionnaireReq> reqs = new ArrayList<>();
            for (int i = 0; i < stringList.size(); i++) {
                FillInQuestionnaireReq req = JSON.parseObject(String.valueOf(stringList.get(i)), FillInQuestionnaireReq.class);
                reqs.add(req);
            }

            // 遍历这个用户填写的问卷题目，获取单个题目的信息
            reqs.forEach(req -> {
                switch (req.getItemType()) {
                    case 1:
                        FillBlank fillBlank = fillBlankService.findById(req.getTopicId());
                        AnswerUserVo answerUserVo = new AnswerUserVo();
                        answerUserVo.setQuestion(fillBlank.getQuestion());
                        answerUserVo.setChoices(req.getAnswerList());
                        answerVoList.add(answerUserVo);
                        break;
                    case 2:
                        Mark mark = markService.findById(req.getTopicId());
                        AnswerUserVo answerUserVo2 = new AnswerUserVo();
                        answerUserVo2.setQuestion(mark.getQuestion());
                        answerUserVo2.setChoices(req.getAnswerList());
                        answerVoList.add(answerUserVo2);
                        break;
                    case 3:
                        MultiChoiceVo multiChoiceVo = multiChoiceService.findVoById(req.getTopicId());
                        AnswerUserVo answerUserVo3 = new AnswerUserVo();
                        answerUserVo3.setQuestion(multiChoiceVo.getQuestion());
                        answerUserVo3.setChoices(req.getAnswerList());
                        answerVoList.add(answerUserVo3);
                        break;
                    case 4:
                        SingleChoiceVo singleChoiceVo = singleChoiceService.findVoById(req.getTopicId());
                        AnswerUserVo answerUserVo4 = new AnswerUserVo();
                        answerUserVo4.setQuestion(singleChoiceVo.getQuestion());
                        answerUserVo4.setChoices(req.getAnswerList());
                        answerVoList.add(answerUserVo4);
                        break;
                    case 5:
                        ApplyChoiceVo applyVoById = singleChoiceService.findApplyVoById(req.getTopicId());
                        List<String> applyVoList = new ArrayList<>();
                        applyVoById.getChoices().forEach(applyOptions -> {
                            if (applyOptions.getId().equals(Integer.parseInt(req.getAnswerList().get(0)))) {
                                applyVoList.add(applyOptions.getName());
                            }
                        });

                        AnswerUserVo answerUserVo5 = new AnswerUserVo();
                        answerUserVo5.setQuestion(applyVoById.getQuestion());
                        answerUserVo5.setChoices(applyVoList);
                        answerVoList.add(answerUserVo5);
                        break;
                    case 6:
                        ApplyChoiceVo applyVoById1 = multiChoiceService.findApplyVoById(req.getTopicId());
                        List<String> applyVoList1 = new ArrayList<>();
                        applyVoById1.getChoices().forEach(applyOptions -> {
                            for (String s : req.getAnswerList()) {
                                Integer i = Integer.parseInt(s);
                                if (i.equals(applyOptions.getId())){
                                    applyVoList1.add(applyOptions.getName());
                                }
                            }
                        });
                        AnswerUserVo answerUserVo6 = new AnswerUserVo();
                        answerUserVo6.setQuestion(applyVoById1.getQuestion());
                        answerUserVo6.setChoices(applyVoList1);
                        answerVoList.add(answerUserVo6);
                        break;
                    default:
                        AnswerUserVo answerUser = new AnswerUserVo();
                        answerUser.setQuestion("改题统计有误，请合法使用系统");
                        answerVoList.add(answerUser);
                }
            });
            voList.add(answerVoList);
        });
        return voList;
    }


    private Questionnaire getQuestionnaireByCode(String code) {
        QueryWrapper<Questionnaire> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        List<Questionnaire> questionnaires = questionnaireMapper.selectList(wrapper);
        if (questionnaires.size() == 0) {
            throw new DefaultException("该问卷不存在或已失效");
        }
        return questionnaires.get(0);
    }
}
