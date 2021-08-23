package com.question.modules.question.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 多选题
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="创建多选题对象", description="创建多选题对象")
public class CreateMultiChoiceReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "多选题内容不能为空")
    @ApiModelProperty(value = "多选题内容")
    private String question;

    @NotNull(message = "多选题选项不能为空")
    @ApiModelProperty(value = "多选题选项")
    private List<String> choices;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @ApiModelProperty(value = "是否为必填")
    private Integer required;

}
