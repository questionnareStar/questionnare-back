package com.question.modules.exam.entities;

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
 *
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exam_single")
@ApiModel(value = "考试单选题对象", description = "考试单选题对象")
public class ExamSingle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    @ApiModelProperty(value = "题目内容")
    private String question;

    @ApiModelProperty(value = "选项")
    private String choices;

    @ApiModelProperty(value = "标准答案")
    private String answer;

    @ApiModelProperty(value = "是否必答")
    private Integer required;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "题目备注")
    @TableField(value = "`desc`")
    private String desc;

    @ApiModelProperty(value = "分值")
    private Integer score;


}
