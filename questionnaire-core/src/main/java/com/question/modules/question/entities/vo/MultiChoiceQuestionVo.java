package com.question.modules.question.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "多选题问卷视图层对象", description = "多选题问卷视图层对象")
public class MultiChoiceQuestionVo implements Serializable {

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

    @ApiModelProperty(value = "题目顺序")
    private Integer sequence;

    @ApiModelProperty(value = "描述信息")
    private String desc;
}
