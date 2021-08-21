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
@TableName("fill_blank")
@ApiModel(value="填空题对象", description="填空题对象")
public class FillBlank implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问卷id")
    private Integer questionnaire;

    @ApiModelProperty(value = "问题")
    private String question;

    @ApiModelProperty(value = "次序")
    private Integer sequence;

    @ApiModelProperty(value = "是否必填")
    private Boolean required;
    @ApiModelProperty(value = "是否删除，1正常 0删除 -1锁定")
    private Integer isDeleted;

}
