package com.question.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Data
@TableName("multi_choice")
@ApiModel(value="多选题对象", description="多选题对象")
public class MultiChoice implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问卷id")
    private Integer questionnaire;

    @ApiModelProperty(value = "问题")
    private String question;

    @ApiModelProperty(value = "选项")
    private String choices;

    @ApiModelProperty(value = "次序")
    private Integer sequence;

    @ApiModelProperty(value = "是否必填")
    private Boolean required;
    @ApiModelProperty(value = "是否删除，1正常 0删除 -1锁定")
    private Integer isDeleted;

}
