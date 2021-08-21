package com.question.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Data
@TableName("question_bank")
@ApiModel(value="题库表对象", description="题库表对象")
public class QuestionBank {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问卷id")
    private Integer questionnaire;

    @ApiModelProperty(value = "问题id")
    private Integer question;

    @ApiModelProperty(value = "问题类型")
    private Integer type;

    @ApiModelProperty(value = "问题次序")
    private Integer sequence;
}

