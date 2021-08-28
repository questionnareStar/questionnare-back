package com.question.modules.exam.entities.vo;

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
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exam_answer_record")
@ApiModel(value = "ExamAnswerRecordVo对象", description = "考试问卷回答记录视图层对象")
public class ExamAnswerRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    @ApiModelProperty(value = "回答用户账号")
    private String userAccount;

    @ApiModelProperty(value = "回答用户id")
    private Integer userId;

    @ApiModelProperty(value = "回答时间")
    private Date answerTime;


}
