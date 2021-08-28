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
 *
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("answer_record")
@ApiModel(value = "AnswerRecord对象", description = "")
public class AnswerRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "此回答对应的问卷id")
    private Integer questionnaireId;

    @ApiModelProperty(value = "回答时间")
    private Date answerTime;

    @ApiModelProperty(value = "回答的用户id，若该问卷不需要登录，即为0")
    private Integer answerUsrId;


}
