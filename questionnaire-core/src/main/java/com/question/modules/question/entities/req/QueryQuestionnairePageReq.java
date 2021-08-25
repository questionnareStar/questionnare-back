package com.question.modules.question.entities.req;

import com.question.domain.BaseRequest;
import com.question.modules.question.entities.Questionnaire;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/21 19:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="查询问卷列表请求对象", description="查询问卷列表请求对象")
public class QueryQuestionnairePageReq extends BaseRequest<Questionnaire> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问卷标题(模糊查询)")
    private String head;

    @ApiModelProperty(value = "限制问卷权限 0：任何人可填写 1：凭邀请码填写 2：登陆后可填写")
    private Integer type;

    @ApiModelProperty(value = "是否发布？ 0 未发布 1 已发布")
    private Integer isReleased;

    @ApiModelProperty(value = "排序方式 0问卷创建时间排序(降序) 1问卷作答数量排序(降序) 2问卷创建时间排序(升序) 3问卷作答数量排序(升序)")
    private Integer sortBy;
}
