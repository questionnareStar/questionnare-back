package com.question.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Data
@TableName("questionnaire")
@ApiModel(value="问卷对象", description="问卷对象")
public class Questionnaire implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String head;

    @ApiModelProperty(value = "用户id")
    @TableField("creator")
    private Integer creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "简介")
    private String introduction;

    @ApiModelProperty(value = "类型 0：任何人可以填写 1：邀请码可填 2：登录可填")
    private Integer type;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "是否开放")
    private Boolean isReleased;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

}
