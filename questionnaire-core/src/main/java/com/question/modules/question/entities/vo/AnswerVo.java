package com.question.modules.question.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "问卷回答结果视图层对象", description = "问卷回答结果视图层对象")
public class AnswerVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问题题目")
    private String question;

    @ApiModelProperty(value = "回答内容")
    private List<AnswerItem<T>> choices;
}
