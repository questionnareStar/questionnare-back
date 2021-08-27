package com.question.modules.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.SingleChoice;
import com.question.modules.question.entities.apply.req.CreateApplyChoiceReq;
import com.question.modules.question.entities.apply.vo.ApplyChoiceVo;
import com.question.modules.question.entities.req.CreateSingleChoiceReq;
import com.question.modules.question.entities.req.UpdateSingleChoiceReq;
import com.question.modules.question.entities.vo.SingleChoiceVo;

/**
 * <p>
 * 单选题 服务类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
public interface ISingleChoiceService extends IService<SingleChoice> {

    /**
     * 创建单选题
     * @param req 单选题内容
     * @return 单选题视图层对象
     */
    SingleChoiceVo insert(CreateSingleChoiceReq req);

    /**
     * 查询单选题内容
     * @param id 单选题id
     * @return 单选题视图层对象
     */
    SingleChoiceVo findVoById(Integer id);

    /**
     * 更新单选题
     * @param req 单选题内容
     * @return 单选题视图层对象
     */
    SingleChoiceVo update(UpdateSingleChoiceReq req);

    /**
     * 更新单选题
     * @param id 多选题id
     * @return 是否删除成功
     */
    Boolean delete(Integer id);


    /**
     * 创建报名对象单选题
     * @param req 单选题信息
     * @return 单选题报名对象
     */
    ApplyChoiceVo insertApply(CreateApplyChoiceReq req);

    ApplyChoiceVo findApplyVoById(Integer id);
}
