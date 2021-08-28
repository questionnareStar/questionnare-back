package com.question.modules.question.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 记录用户填写的信息
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_answer")
@ApiModel(value = "UserAnswer对象", description = "记录用户填写的信息")
public class UserAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问卷id")
    private Integer questionId;

    @ApiModelProperty(value = "用户填写的问卷信息")
    private String answer;


}
