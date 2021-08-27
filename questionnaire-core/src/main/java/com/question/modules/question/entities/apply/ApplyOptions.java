package com.question.modules.question.entities.apply;

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
 * 多选题，选项表
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("apply_options")
@ApiModel(value = "ApplyOptions选项对象", description = "选项内容信息")
public class ApplyOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "选项内容")
    private String name;

    @ApiModelProperty(value = "可选数量")
    private Integer number;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "已选数量")
    private Integer selected;

    @TableField(exist = false)
    @ApiModelProperty(value = "剩余数量")
    private Integer surplus;

    public Integer getSurplus() {
        return number - selected;
    }
}
