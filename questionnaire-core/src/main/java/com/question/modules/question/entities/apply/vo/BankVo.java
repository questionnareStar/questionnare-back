package com.question.modules.question.entities.apply.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 题库表
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "题库视图对象", description = "题库视图对象")
public class BankVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型id（1.填空题 2.评分题 3.多选题 4.单选题）")
    private Integer type;

    @ApiModelProperty(value = "题目id")
    private Integer topicId;

    @ApiModelProperty(value = "题目顺序")
    private Integer sequence;


}
