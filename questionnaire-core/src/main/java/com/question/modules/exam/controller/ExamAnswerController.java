package com.question.modules.exam.controller;

import com.question.modules.exam.entities.req.AnswerExamQuestionReq;
import com.question.modules.exam.entities.vo.FeedBackVo;
import com.question.modules.exam.service.IExamAnswerRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考试问卷 前端控制器
 *
 * @author 问卷星球团队
 * @since 2021-08-27
 */
@Api(tags = "填写考试问卷相关接口")
@RestController
@RequestMapping("/api/v1/exam/answer")
public class ExamAnswerController {

    @Autowired
    private IExamAnswerRecordService answerRecordService;

    @ApiOperation("填写报名问卷")
    @PostMapping("/fill/in/{code}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "问卷密钥")
    })
    public boolean fillQuestionnaire(@PathVariable String code, @RequestBody @Validated List<AnswerExamQuestionReq> reqs){
        return answerRecordService.fillIn(code,reqs);
    }

}
