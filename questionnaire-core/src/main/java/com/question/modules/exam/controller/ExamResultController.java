package com.question.modules.exam.controller;

import com.question.modules.exam.entities.vo.ExamQuestionnaireAnswerVo;
import com.question.modules.exam.entities.vo.ExamQuestionnaireDetailVo;
import com.question.modules.exam.entities.vo.FeedBackVo;
import com.question.modules.exam.entities.vo.statistics.ExamStatisticsVo;
import com.question.modules.exam.service.IExamAnswerRecordService;
import com.question.modules.exam.service.IExamQuestionBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 考试问卷 前端控制器
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Api(tags = "考试问卷结果相关接口")
@RestController
@RequestMapping("/api/v1/exam/result")
public class ExamResultController {
    @Autowired
    private IExamQuestionBankService examQuestionBankService;
    @Autowired
    private IExamAnswerRecordService examAnswerRecordService;

    @ApiOperation("查询问卷填写详细情况")
    @PostMapping("/find/answer/{id}")
    public FeedBackVo getParticipateUsers(@PathVariable Integer id) {
        return examAnswerRecordService.getAllAnswerRecordByQuestionnaireId(id);
    }

    @ApiOperation("查询某份填写的回答详细以及评分")
    @PostMapping("/score")
    public ExamQuestionnaireAnswerVo getScore(@RequestParam Integer user_id, @RequestParam Integer questionnaire_id) {
        return examAnswerRecordService.getUserScore(user_id, questionnaire_id);
    }

    @ApiOperation("查询问卷详情")
    @PostMapping("detail/{code}")
    public ExamQuestionnaireDetailVo getDetail(@PathVariable @Validated String code) {
        return examQuestionBankService.getDetailedQuestionnaire(code);
    }

    @ApiOperation("查询问卷数据统计结果")
    @PostMapping("statistics/{id}")
    public ExamStatisticsVo getQuestionnaireStatics(@PathVariable @Validated Integer id) {
        return examQuestionBankService.getQuestionnaireStatistics(id);
    }
}
