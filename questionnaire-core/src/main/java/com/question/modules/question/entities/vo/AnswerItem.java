package com.question.modules.question.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/22 20:05
 */
@Data
@ApiModel(value="问卷回答内容视图层对象", description="问卷回答内容视图层对象")
public class AnswerItem<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "回答答案")
    private T item;

    @ApiModelProperty(value = "回答数量")
    private Integer num;
}
