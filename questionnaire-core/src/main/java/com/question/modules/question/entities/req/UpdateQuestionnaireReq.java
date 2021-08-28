package com.question.modules.question.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/21 19:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="更新问卷请求对象", description="更新问卷请求对象")
public class UpdateQuestionnaireReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "问卷id不能为空")
    @ApiModelProperty(value = "问卷id")
    private Integer id;

    @NotNull(message = "问卷标题不能为空")
    @ApiModelProperty(value = "问卷标题")
    private String head;

    @ApiModelProperty(value = "问卷介绍")
    private String introduction;

    @NotNull(message = "问卷开始收集时间不能为空")
    @ApiModelProperty(value = "问卷开始收集时间")
    private Date startTime;

    @NotNull(message = "问卷结束收集时间不能为空")
    @ApiModelProperty(value = "问卷结束收集时间")
    private Date endTime;

    @NotNull(message = "是否立即发布？")
    @ApiModelProperty(value = "是否发布？ 0 未发布 1 已发布")
    private Integer isReleased;

    @NotNull(message = "是否显示题号？")
    @ApiModelProperty(value = "是否显示题号 true显示 false不显示")
    private boolean isSerial;

    @NotNull(message = "问卷类型不可为空")
    @ApiModelProperty(value = "问卷类型 1普通问卷 2投票问卷 3报名问卷 4疫情打卡问卷")
    private Integer stamp;

    @ApiModelProperty(value = "总数量")
    private Integer maxNum;

    @NotNull(message = "题目不能为空")
    @ApiModelProperty(value = "题目集合")
    private List<HeadingItemReq> itemList;

}
