package com.question.modules.question.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "某日问卷回答人数视图层对象", description = "某日问卷回答人数视图层对象")
public class AnswerNumber {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日期")
    private Date date;

    @ApiModelProperty(value = "数量")
    private Integer number;


}
