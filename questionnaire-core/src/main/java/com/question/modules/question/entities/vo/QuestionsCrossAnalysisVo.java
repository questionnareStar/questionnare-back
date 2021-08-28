package com.question.modules.question.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 交叉分析结果
 *
 * @author 问卷星球团队
 * @since 2021-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="问卷详情视图层对象", description="问卷详情视图层对象")
public class QuestionsCrossAnalysisVo {

    @ApiModelProperty(value = "问题1的问题")
    String question1;

    @ApiModelProperty(value = "问题2的问题")
    String question2;

    @ApiModelProperty(value = "问题1的选项")
    List<String> question1Choices;

    @ApiModelProperty(value = "问题2的选项")
    List<String> question2Choices;

    @ApiModelProperty(value = "问题交叉分析的结果")
    List<List<Integer>> analysisResult;

}
