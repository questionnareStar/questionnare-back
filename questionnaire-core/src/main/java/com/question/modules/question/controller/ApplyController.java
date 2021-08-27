package com.question.modules.question.controller;

import com.question.modules.question.entities.apply.req.CreateApplyChoiceReq;
import com.question.modules.question.entities.apply.vo.ApplyChoiceVo;
import com.question.modules.question.entities.apply.vo.QuestionnaireSimpleVo;
import com.question.modules.question.entities.vo.AnswerUserVo;
import com.question.modules.question.service.IMultiChoiceService;
import com.question.modules.question.service.IQuestionnaireService;
import com.question.modules.question.service.ISingleChoiceService;
import com.question.modules.question.service.IUserAnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 问卷星球团队
 * @version 1.0
 * @date 2021/8/26 14:52
 */
@Api(tags = "报名问卷相关接口")
@RestController
@RequestMapping("/api/v1/apply/questionnaire")
public class ApplyController {

    @Autowired
    private IMultiChoiceService multiChoiceService;

    @Autowired
    private ISingleChoiceService singleChoiceService;

    @Autowired
    private IQuestionnaireService questionnaireService;

    @Autowired
    private IUserAnswerService userAnswerService;

    @ApiOperation("创建报名问卷多选题")
    @PostMapping("/create/multi/choice")
    public ApplyChoiceVo createMultiChoice(@RequestBody @Validated CreateApplyChoiceReq req) {
        return multiChoiceService.insertApply(req);
    }

    @ApiOperation("根据id查询报名问卷多选题")
    @GetMapping("/find/multi/choice/{id}")
    public ApplyChoiceVo createMultiChoice(@PathVariable Integer id) {
        return multiChoiceService.findApplyVoById(id);
    }


    @ApiOperation("创建报名问卷单选题")
    @PostMapping("/create/single/choice")
    public ApplyChoiceVo createSingleChoice(@RequestBody @Validated CreateApplyChoiceReq req) {
        return singleChoiceService.insertApply(req);
    }

    @ApiOperation("根据id查询报名问卷单选题")
    @GetMapping("/find/single/choice/{id}")
    public ApplyChoiceVo createSingleChoice(@PathVariable Integer id) {
        return singleChoiceService.findApplyVoById(id);
    }

    @ApiOperation("查询问卷详情（只返回题目类型和id）")
    @GetMapping("/detail/{code}")
    @ApiImplicitParam(name = "code", value = "问卷密钥")
    public QuestionnaireSimpleVo detailQuestion(@PathVariable String code) {
        return questionnaireService.detailApplyQuestion(code);
    }

    @ApiOperation("查询问卷每个用户回答的内容")
    @GetMapping("/answer/{code}")
    @ApiImplicitParam(name = "code", value = "问卷密钥")
    public List<List<AnswerUserVo>> findQuestionAnswer(@PathVariable String code) {
        return userAnswerService.findQuestionAnswer(code);
    }

}
