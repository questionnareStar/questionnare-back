package com.question.modules.question.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 填空题
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("fill_blank")
@ApiModel(value = "FillBlank对象", description = "填空题")
public class FillBlank implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问题")
    private String question;

    @ApiModelProperty(value = "是否为必填 0false 1true 1表示必填")
    private boolean required;

    @ApiModelProperty(value = "是否删除  0false 1true 1表示删除")
    private Boolean isDeleted;

    @TableField("`desc`")
    @ApiModelProperty(value = "描述信息")
    private String desc;

}
