package com.question.modules.question.controller;

import com.alibaba.fastjson.JSONObject;
import com.question.modules.question.entities.vo.AnswerNumberVo;
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
public class StaticsController {
    @Autowired
    private IQuestionBankService questionBankService;
    @Autowired
    private IAnswerRecordService answerRecordService;

    @ApiOperation("获取问卷详细数据")
    @PostMapping("/get")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireId", value = "问卷id"),
    })
    public JSONObject getQuestionnaireStatics(@RequestParam  Integer questionnaireId) {
        return questionBankService.createQuestionnaireStatic(questionnaireId);
    }

    @ApiOperation("获取问卷填写量数据")
    @PostMapping("/answer/number")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireId", value = "问卷id"),
    })
    public AnswerNumberVo getQuestionnaireAnswerNumber(@RequestParam  Integer questionnaireId) {
        return answerRecordService.createQuestionnaireAnswerNumber(questionnaireId);
    }
}
