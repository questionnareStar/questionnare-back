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
 * <p>
 * 评分题
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mark")
@ApiModel(value="Mark对象", description="评分题")
public class Mark implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问题")
    private String question;

    @ApiModelProperty(value = "总分")
    private Integer maxScore;

    @ApiModelProperty(value = "是否为必填")
    private Integer required;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @TableField("`desc`")
    @ApiModelProperty(value = "描述信息")
    private String desc;
}
