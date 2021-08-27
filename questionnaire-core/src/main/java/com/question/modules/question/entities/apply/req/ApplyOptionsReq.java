package com.question.modules.question.entities.apply.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 多选题，选项表
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="选项对象", description="选项对象")
public class ApplyOptionsReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "选项内容")
    private String name;

    @ApiModelProperty(value = "可选数量")
    private Integer number;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
