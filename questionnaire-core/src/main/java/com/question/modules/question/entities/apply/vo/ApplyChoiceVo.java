package com.question.modules.question.entities.apply.vo;

import com.question.modules.question.entities.apply.ApplyOptions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "选择题报名问卷视图层对象", description = "选择题报名问卷视图层对象")
public class ApplyChoiceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "问题")
    private String question;

    @ApiModelProperty(value = "选项信息")
    private List<ApplyOptions> choices;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @ApiModelProperty(value = "是否为必填")
    private Integer required;
}
