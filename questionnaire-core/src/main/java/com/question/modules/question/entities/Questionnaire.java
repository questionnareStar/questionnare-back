package com.question.modules.question.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 问卷表
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("questionnaire")
@ApiModel(value="Questionnaire对象", description="问卷表")
public class Questionnaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问卷标题")
    private String head;

    @ApiModelProperty(value = "创建人")
    private Integer userId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "问卷介绍")
    private String introduction;

    @ApiModelProperty(value = "问卷类型 0：任何人可填写 1：凭邀请码填写 2：登陆后可填写")
    private Integer type;

    @ApiModelProperty(value = "是否删除")
    private boolean isDeleted;

    @ApiModelProperty(value = "0 未发布 1 已发布")
    private Integer isReleased;

    @ApiModelProperty(value = "问卷开始收集时间")
    private Date startTime;

    @ApiModelProperty(value = "问卷结束收集时间")
    private Date endTime;

    @ApiModelProperty(value = "问卷填写数量")
    private Integer writeNum;

    @ApiModelProperty(value = "问卷邀请码")
    private String code;

    @ApiModelProperty(value = "是否显示题号 true显示 false不显示")
    private boolean isSerial;
}
