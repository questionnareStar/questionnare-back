package com.question.modules.question.controller;

import com.alibaba.fastjson.JSONObject;
import com.question.modules.question.entities.vo.AnswerNumberVo;
import com.question.modules.question.entities.vo.QuestionnaireAnswerVo;
import com.question.modules.question.entities.vo.QuestionsCrossAnalysisVo;
import com.question.modules.question.service.IAnswerRecordService;
import com.question.modules.question.service.IQuestionBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据统计 前端控制器
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Api(tags = "数据统计相关接口")
@RestController
@Transactional
@RequestMapping("/api/v1/statics")
public class StatisticsController {
    @Autowired
    private IQuestionBankService questionBankService;
    @Autowired
    private IAnswerRecordService answerRecordService;

    @ApiOperation("获取问卷详细数据")
    @PostMapping("/get")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireId", value = "问卷id"),
    })
    public JSONObject getQuestionnaireStatics(@RequestParam Integer questionnaireId) {
        return questionBankService.createQuestionnaireStatic(questionnaireId);
    }

    @ApiOperation("获取问卷填写量数据")
    @PostMapping("/answer/number")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireId", value = "问卷id"),
    })
    public AnswerNumberVo getQuestionnaireAnswerNumber(@RequestParam Integer questionnaireId) {
        return answerRecordService.createQuestionnaireAnswerNumber(questionnaireId);
    }

    @ApiOperation("获取回答记录的详细填写结果")
    @PostMapping("/answer/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireId", value = "问卷id"),
            @ApiImplicitParam(name = "recordId", value = "回答记录id"),
    })
    public QuestionnaireAnswerVo getAnswerRecordDetail(@RequestParam Integer questionnaireId, @RequestParam Integer recordId) {
        return answerRecordService.getAnswerRecordDetailInfo(questionnaireId, recordId);
    }


    @ApiOperation("获取两个问题的交叉分析结果")
    @PostMapping("/cross/analysis")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireId", value = "问卷id"),
            @ApiImplicitParam(name = "type1", value = "问题1的类型 3多选题 4单选题"),
            @ApiImplicitParam(name = "question1Id", value = "问题1的编号"),
            @ApiImplicitParam(name = "type2", value = "问题2的类型 3多选题 4单选题"),
            @ApiImplicitParam(name = "question2Id", value = "问题2的编号"),
    })
    public QuestionsCrossAnalysisVo getCrossAnalysis(@RequestParam Integer questionnaireId, @RequestParam Integer type1, @RequestParam Integer question1Id, @RequestParam Integer type2, @RequestParam Integer question2Id) {
        return answerRecordService.getCrossAnalysisResult(questionnaireId, type1, question1Id, type2, question2Id);
    }


}
