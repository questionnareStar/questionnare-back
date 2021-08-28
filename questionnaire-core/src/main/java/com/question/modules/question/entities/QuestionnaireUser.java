package com.question.modules.question.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户回答表
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("questionnaire_user")
@ApiModel(value = "QuestionnaireUser对象", description = "用户回答表")
public class QuestionnaireUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问卷id")
    private Integer questionnaireId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;


}
