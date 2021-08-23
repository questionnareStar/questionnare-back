package com.question.modules.question.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 单选题
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="单选题问卷视图层对象", description="单选题问卷视图层对象")
public class SingleQuestionChoiceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "题目类型")
    private Integer itemType;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "问题")
    private String question;

    @ApiModelProperty(value = "选项")
    private List<String> choices;

    @ApiModelProperty(value = "是否为必填")
    private Integer required;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @ApiModelProperty(value = "题目顺序")
    private Integer sequence;
}
