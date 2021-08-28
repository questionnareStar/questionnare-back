package com.question.modules.exam.controller;

import com.question.modules.exam.entities.req.*;
import com.question.modules.exam.entities.vo.ExamFillInVo;
import com.question.modules.exam.entities.vo.ExamMultiVo;
import com.question.modules.exam.entities.vo.ExamSingleVo;
import com.question.modules.exam.service.IExamFillInService;
import com.question.modules.exam.service.IExamMultiService;
import com.question.modules.exam.service.IExamQuestionBankService;
import com.question.modules.exam.service.IExamSingleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(tags = "考试问卷题目相关接口")
@RestController
@RequestMapping("/api/v1/exam/question")
public class ExamTopicController {
    @Autowired
    private IExamQuestionBankService examQuestionBankService;

    @Autowired
    private IExamSingleService examSingleService;

    @Autowired
    private IExamMultiService examMultiService;

    @Autowired
    private IExamFillInService examFillInService;

    @ApiOperation("创建考试单选题")
    @PostMapping("/create/exam/single/choice")
    public ExamSingleVo createSingle(@RequestBody @Validated CreateExamSingleReq req) {
        return examSingleService.create(req);
    }

    @ApiOperation("更新考试单选题")
    @PostMapping("/update/exam/single/choice")
    public ExamSingleVo updateSingle(@RequestBody @Validated UpdateExamSingleReq req) {
        return examSingleService.updateSingle(req);
    }

    @ApiImplicitParam(name = "id", value = "要删除的题目id")
    @ApiOperation("删除考试单选题,成功返回true，id不正确返回false")
    @PostMapping("/delete/exam/single/{id}")
    public Boolean deleteSingle(@PathVariable Integer id) {
        return examSingleService.deleteSingleById(id);
    }

    @ApiOperation("创建考试多选题")
    @PostMapping("/create/exam/multi/choice")
    public ExamMultiVo createMulti(@RequestBody @Validated CreateExamMultiReq req) {
        return examMultiService.create(req);
    }

    @ApiOperation("更新考试多选题")
    @PostMapping("/update/exam/multi/choice")
    public ExamMultiVo updateMulti(@RequestBody @Validated UpdateExamMultiReq req) {
        return examMultiService.updateMulti(req);
    }

    @ApiImplicitParam(name = "id", value = "要删除的题目id")
    @ApiOperation("删除考试多选题,成功返回true，id不正确返回false")
    @PostMapping("/delete/exam/multi/{id}")
    public Boolean deleteMulti(@PathVariable Integer id) {
        return examMultiService.deleteSingleById(id);
    }

    @ApiOperation("创建考试填空题")
    @PostMapping("/create/exam/fill/in")
    public ExamFillInVo createFillIn(@RequestBody @Validated CreateExamFillInReq req) {
        return examFillInService.create(req);
    }

    @ApiOperation("更新考试填空题")
    @PostMapping("/update/exam/fill/in")
    public ExamFillInVo updateFillIn(@RequestBody @Validated UpdateExamFillInReq req) {
        return examFillInService.updateFillIn(req);
    }

    @ApiImplicitParam(name = "id", value = "要删除的题目id")
    @ApiOperation("删除考试填空题,成功返回true，id不正确返回false")
    @PostMapping("/delete/exam/fill/in/{id}")
    public Boolean deleteFillIn(@PathVariable Integer id) {
        return examFillInService.deleteSingleById(id);
    }
}
