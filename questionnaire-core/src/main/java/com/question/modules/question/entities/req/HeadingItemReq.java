package com.question.modules.question.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/21 19:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "题目前端请求对象", description = "题目前端请求对象")
public class HeadingItemReq implements Serializable {

    @ApiModelProperty(value = "题目id")
    private Integer itemId;

    @ApiModelProperty(value = "题目类型 1.填空题 2.评分题 3.多选题 4.单选题 5.报名问卷单选题 6.报名问卷多选题")
    private Integer itemType;

    @ApiModelProperty(value = "题目顺序")
    private Integer itemOrder;
}
