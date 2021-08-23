package com.question.modules.question.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.entities.req.CreateQuestionnaireReq;
import com.question.modules.question.entities.req.FillInQuestionnaireReq;
import com.question.modules.question.entities.req.QueryQuestionnairePageReq;
import com.question.modules.question.entities.req.UpdateQuestionnaireReq;
import com.question.modules.question.entities.vo.AnswerVo;
import com.question.modules.question.entities.vo.QuestionnaireDetailVo;
import com.question.modules.question.service.IQuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 问卷表 前端控制器
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Api(tags = "问卷相关接口")
@RestController
@RequestMapping("/api/v1/questionnaire")
public class QuestionnaireController {

    @Autowired
    private IQuestionnaireService questionnaireService;

    @ApiOperation("创建普通问卷-无需登录")
    @PostMapping("/create")
    public Questionnaire createQuestionnaire(@RequestBody @Validated CreateQuestionnaireReq req) {
        return questionnaireService.createQuestionnaire(req, 0);
    }

    @ApiOperation("创建问卷-登录后才能填写")
    @PostMapping("/create/login")
    public Questionnaire createQuestionnaireLogin(@RequestBody @Validated CreateQuestionnaireReq req) {
        return questionnaireService.createQuestionnaire(req, 2);
    }

    @ApiOperation("创建问卷-登录+验证码")
    @PostMapping("/create/login/code")
    public Questionnaire createQuestionnaireLoginAnd(@RequestBody @Validated CreateQuestionnaireReq req) {
        return questionnaireService.createQuestionnaire(req, 3);
    }

    @ApiOperation("创建问卷-邀请码方式")
    @PostMapping("/create/code")
    public Questionnaire createQuestionnaireCode(@RequestBody @Validated CreateQuestionnaireReq req) {
        return questionnaireService.createQuestionnaireCode(req);
    }


    @ApiOperation("查询我的问卷列表")
    @PostMapping("/find/me/all")
    public IPage<Questionnaire> getQuestionnaireList(@RequestBody @Validated QueryQuestionnairePageReq req) {
        return questionnaireService.getQuestionnaireList(req);
    }

    @ApiOperation("查询我的问卷回收站列表")
    @PostMapping("/find/me/old")
    public IPage<Questionnaire> getQuestionnaireOldPage(@RequestBody @Validated QueryQuestionnairePageReq req) {
        return questionnaireService.getQuestionnaireListOld(req);
    }

    @ApiOperation("删除问卷到回收站")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public boolean deleteById(@PathVariable String id) {
        return questionnaireService.deleteById(id);
    }

    @ApiOperation("从回收站恢复问卷")
    @PutMapping("/restore/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public boolean restoreById(@PathVariable String id) {
        return questionnaireService.restoreById(id);
    }

    @ApiOperation("永久删除问卷(不可恢复)")
    @DeleteMapping("/death/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public boolean deathById(@PathVariable String id) {
        return questionnaireService.deathById(id);
    }

    @ApiOperation("开启问卷(发布问卷)")
    @GetMapping("/open/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public boolean openQuestion(@PathVariable String id) {
        return questionnaireService.open(id);
    }

    @ApiOperation("关闭问卷(取消发布)")
    @GetMapping("/close/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public boolean closeQuestion(@PathVariable String id) {
        return questionnaireService.close(id);
    }

    @ApiOperation("复制问卷-不删除")
    @GetMapping("/copy/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public Questionnaire copyQuestion(@PathVariable String id) {
        return questionnaireService.copyQuestion(id,0);
    }

    @ApiOperation("复制问卷-原问卷放入回收站")
    @GetMapping("/copy/del/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public Questionnaire copyAndDelQuestion(@PathVariable String id) {
        return questionnaireService.copyQuestion(id,1);
    }

    @ApiOperation("查询问卷详情")
    @GetMapping("/detail/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public QuestionnaireDetailVo detailQuestion(@PathVariable String id) {
        return questionnaireService.detailQuestion(id);
    }


    @ApiOperation("填写问卷")
    @PostMapping("/fillIn/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问卷id"),
            @ApiImplicitParam(name = "code", value = "邀请码，使用?拼接到路径后，如果是不需要验证码类型，不传递即可")
    })
    public boolean fillIn(@PathVariable String id, @RequestBody @Validated List<FillInQuestionnaireReq> reqs, String code) {
        return questionnaireService.fillIn(id, reqs, code);
    }

    @ApiOperation("查询用户是否具有填写问卷的资格")
    @GetMapping("/fillIn/flag/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问卷id"),
            @ApiImplicitParam(name = "code", value = "邀请码，使用?拼接到路径后，如果是不需要验证码类型，不传递即可")
    })
    public boolean fillInIsFlag(@PathVariable String id, String code) {
        return questionnaireService.fillInIsFlag(id, code);
    }

    @ApiOperation("查询问卷调查结果")
    @GetMapping("/find/answer/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public List<AnswerVo> getAnswer(@PathVariable String id) {
        return questionnaireService.getAnswer(id);
    }


    @ApiOperation("修改问卷")
    @PutMapping("/update")
    public Questionnaire updateQuestionnaire(@RequestBody @Validated UpdateQuestionnaireReq req) {
        return questionnaireService.updateQuestionnaire(req);
    }
}
