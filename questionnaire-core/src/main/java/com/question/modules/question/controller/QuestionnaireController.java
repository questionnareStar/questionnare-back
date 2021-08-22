package com.question.modules.question.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.entities.req.CreateQuestionnaireReq;
import com.question.modules.question.entities.req.QueryQuestionnairePageReq;
import com.question.modules.question.entities.vo.QuestionnaireDetailVo;
import com.question.modules.question.service.IQuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("创建问卷")
    @PostMapping("/create")
    public Questionnaire createQuestionnaire(@RequestBody @Validated CreateQuestionnaireReq req) {
        return questionnaireService.createQuestionnaire(req);
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

    @ApiOperation("永久删除文件信息(不可恢复)")
    @DeleteMapping("/death/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public boolean deathById(@PathVariable String id) {
        return questionnaireService.deathById(id);
    }

    @ApiOperation("开启问卷(发布问卷)")
    @GetMapping("/open/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public boolean openQuestion(@PathVariable String id){
        return questionnaireService.open(id);
    }

    @ApiOperation("关闭问卷(取消发布)")
    @GetMapping("/close/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public boolean closeQuestion(@PathVariable String id){
        return questionnaireService.close(id);
    }

    @ApiOperation("复制问卷")
    @GetMapping("/copy/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public Questionnaire copyQuestion(@PathVariable String id){
        return questionnaireService.copyQuestion(id);
    }

    @ApiOperation("查询问卷详情")
    @GetMapping("/detail/{id}")
    @ApiImplicitParam(name = "id", value = "问卷id")
    public QuestionnaireDetailVo detailQuestion(@PathVariable String id){
        return questionnaireService.detailQuestion(id);
    }

}
