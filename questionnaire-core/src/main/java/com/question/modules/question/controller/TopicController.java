package com.question.modules.question.controller;


import com.question.modules.question.entities.FillBlank;
import com.question.modules.question.entities.Mark;
import com.question.modules.question.entities.req.*;
import com.question.modules.question.entities.vo.MultiChoiceVo;
import com.question.modules.question.entities.vo.SingleChoiceVo;
import com.question.modules.question.service.IFillBlankService;
import com.question.modules.question.service.IMarkService;
import com.question.modules.question.service.IMultiChoiceService;
import com.question.modules.question.service.ISingleChoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 题目相关接口 前端控制器
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Api(tags = "题目相关接口")
@RestController
@RequestMapping("/api/v1/topic")
public class TopicController {

    @Autowired
    private IFillBlankService fillBlankService;

    @Autowired
    private IMarkService markService;

    @Autowired
    private IMultiChoiceService multiChoiceService;

    @Autowired
    private ISingleChoiceService singleChoiceService;

    @ApiOperation("创建填空题")
    @PostMapping("/create/fill/blank")
    public FillBlank createFillBlank(@RequestBody @Validated CreateFillBlankReq req) {
        return fillBlankService.insert(req);
    }

    @ApiOperation("删除填空题")
    @PostMapping("/delete/fill/blank")
    public Boolean deleteFillBlank(@RequestParam @Validated Integer id) {
        return fillBlankService.delete(id);
    }

    @ApiOperation("根据id查询填空题内容")
    @GetMapping("/find/fill/blank/{id}")
    public FillBlank findFillBlankById(@PathVariable Integer id) {
        return fillBlankService.findById(id);
    }

    @ApiOperation("创建评分题")
    @PostMapping("/create/mark")
    public Mark createMark(@RequestBody @Validated CreateMarkReq req) {
        return markService.insert(req);
    }

    @ApiOperation("删除评分题")
    @PostMapping("/delete/mark")
    public Boolean deleteMark(@RequestParam @Validated Integer id) {
        return markService.delete(id);
    }

    @ApiOperation("根据id查询评分题目内容")
    @GetMapping("/find/mark/{id}")
    public Mark findMarkById(@PathVariable Integer id) {
        return markService.findById(id);
    }

    @ApiOperation("创建多选题")
    @PostMapping("/create/multi/choice")
    public MultiChoiceVo createMultiChoice(@RequestBody @Validated CreateMultiChoiceReq req) {
        return multiChoiceService.insert(req);
    }

    @ApiOperation("删除多选题")
    @PostMapping("/delete/multi/choice")
    public Boolean deleteMultiChoice(@RequestParam @Validated Integer id) {
        return multiChoiceService.delete(id);
    }

    @ApiOperation("根据id查询多选题")
    @GetMapping("/find/multi/choice/{id}")
    public MultiChoiceVo createMultiChoice(@PathVariable Integer id) {
        return multiChoiceService.findVoById(id);
    }


    @ApiOperation("创建单选题")
    @PostMapping("/create/single/choice")
    public SingleChoiceVo createSingleChoice(@RequestBody @Validated CreateSingleChoiceReq req) {
        return singleChoiceService.insert(req);
    }

    @ApiOperation("删除单选题")
    @PostMapping("/delete/single/choice")
    public Boolean deleteSingleChoice(@RequestParam @Validated Integer id) {
        return singleChoiceService.delete(id);
    }

    @ApiOperation("根据id查询单选题")
    @GetMapping("/find/single/choice/{id}")
    public SingleChoiceVo createSingleChoice(@PathVariable Integer id) {
        return singleChoiceService.findVoById(id);
    }

    @ApiOperation("更新填空题")
    @PutMapping("/update/fill/blank")
    public FillBlank updateFillBlank(@RequestBody @Validated UpdateFillBlankReq req) {
        return fillBlankService.update(req);
    }

    @ApiOperation("更新评分题")
    @PutMapping("/update/mark")
    public Mark updateMark(@RequestBody @Validated UpdateMarkReq req) {
        return markService.update(req);
    }

    @ApiOperation("更新多选题")
    @PutMapping("/update/multi/choice")
    public MultiChoiceVo updateMultiChoice(@RequestBody @Validated UpdateMultiChoiceReq req) {
        return multiChoiceService.update(req);
    }

    @ApiOperation("更新单选题")
    @PutMapping("/update/single/choice")
    public SingleChoiceVo updateSingleChoice(@RequestBody @Validated UpdateSingleChoiceReq req) {
        return singleChoiceService.update(req);
    }
}
