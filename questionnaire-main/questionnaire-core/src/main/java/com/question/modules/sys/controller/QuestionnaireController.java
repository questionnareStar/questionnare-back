package com.question.modules.sys.controller;

import com.question.modules.sys.entity.*;
import com.question.modules.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 前端控制器-问卷
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Api(tags = "问卷层接口")
@RestController
@RequestMapping("/api/v1/user")
public class QuestionnaireController {
    private final String divide_char = "`";

    @Autowired
    private ISingleChoiceService singleChoiceService;

    @Autowired
    private IMultiChoiceService multiChoiceService;


    @Autowired
    private IMarkService markService;

    @Autowired
    private IFillBlankService fillBlankService;

    @Autowired
    private IQuestionnaireService questionnaireService;

    @Autowired
    private IQuestionBankService questionBankService;

    @ApiOperation("创建问卷")
    @PostMapping("/createQuestionnaire")
    @Transactional
    public Map<String, Object> createQuestionnaire(@RequestBody Map<String, Object> map) {
        Map<String, Object> rtn = new HashMap<>();
        //1.获取所有问卷参数
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setHead((String) map.get("head"));
        questionnaire.setIntroduction((String) map.get("introduction"));
        questionnaire.setCreator((Integer) map.get("user"));
        questionnaire.setType((Integer) map.get("authority"));
        questionnaire.setIsDeleted(false);
        questionnaire.setIsReleased(false);
        questionnaire.setStartTime(new Date(Long.parseLong((String) map.get("startTime"))));
        questionnaire.setCreateTime(new Date());
        questionnaire.setEndTime(new Date(Long.parseLong((String) map.get("endTime"))));
        //2.向数据库插入问卷并取得id
        int questionnaireId = questionnaireService.InsertQuestionnaire(questionnaire);
        //3.获取问卷中所有问题
        List<Map<String, Object>> questions = (List<Map<String, Object>>) (map.get("questions"));
        //4.确保每个选择题都有选项
        for (int i = 0; i < questions.size(); i++) {
            Map<String, Object> question = questions.get(i);
            Integer type = (Integer) question.get("type");
            if (type == 0 || type == 1) {
                List<String> choices = (List<String>) question.get("choices");
                if (choices.size() == 0) {
                    rtn.put("succeed", false);
                    rtn.put("message", "question " + i + " has no choices!");
                    return rtn;
                }
            }
        }
        //5.创建问题对象
        for (int i = 0; i < questions.size(); i++) {
            int questionId = 0;
            Map<String, Object> question = questions.get(i);
            Integer type = (Integer) question.get("type");
            switch (type) {
                case 0: {
                    SingleChoice singleChoice = new SingleChoice();
                    singleChoice.setIsDeleted(0);
                    singleChoice.setRequired((Boolean) question.get("isRequired"));
                    singleChoice.setQuestion((String) question.get("question"));
                    List<String> choices = (List<String>) question.get("choices");
                    singleChoice.setChoices(parseChoicesToString(choices));
                    questionId = singleChoiceService.InsertSingleChoice(singleChoice);
                    break;
                }
                case 1: {
                    MultiChoice multiChoice = new MultiChoice();
                    multiChoice.setIsDeleted(0);
                    multiChoice.setRequired((Boolean) question.get("isRequired"));
                    multiChoice.setQuestion((String) question.get("question"));
                    List<String> choices = (List<String>) question.get("choices");
                    multiChoice.setChoices(parseChoicesToString(choices));
                    questionId = multiChoiceService.InsertMultiChoice(multiChoice);
                    break;
                }
                case 2: {
                    FillBlank fillBlank = new FillBlank();
                    fillBlank.setIsDeleted(0);
                    fillBlank.setRequired((Boolean) question.get("isRequired"));
                    fillBlank.setQuestion((String) question.get("question"));
                    questionId = fillBlankService.InsertFillBlank(fillBlank);
                    break;
                }
                case 3: {
                    Mark mark = new Mark();
                    mark.setIsDeleted(0);
                    mark.setRequired((Boolean) question.get("isRequired"));
                    mark.setQuestion((String) question.get("question"));
                    mark.setMaxScore((Integer) question.get("maxScore"));
                    questionId = markService.InsertMark(mark);
                    break;
                }
            }
            QuestionBank questionBank = new QuestionBank();
            questionBank.setQuestionnaire(questionnaireId);
            questionBank.setQuestion(questionId);
            questionBank.setSequence(i+1);
            questionBank.setType(type);
            questionBankService.InsertQuestionBank(questionBank);
        }

        //返回创建结果
        int id_of_questionnaire = 1;
        rtn.put("succeed", true);
        rtn.put("message", id_of_questionnaire);
        return rtn;


    }


    public String parseChoicesToString(List<String> choices) {
        StringBuilder stringBuilder = new StringBuilder(choices.get(0));
        for (int j = 1; j < choices.size(); j++) {
            stringBuilder.append("`");
            stringBuilder.append(choices.get(j));
        }
        return stringBuilder.toString();
    }

    public List<String> parseStringToChoices(String s) {
        String[] choices = s.split(divide_char);
        return Arrays.asList(choices);
    }
}
