package com.question.modules.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.MultiChoice;
import com.question.modules.question.entities.apply.req.CreateApplyChoiceReq;
import com.question.modules.question.entities.apply.vo.ApplyChoiceVo;
import com.question.modules.question.entities.req.CreateMultiChoiceReq;
import com.question.modules.question.entities.req.UpdateMultiChoiceReq;
import com.question.modules.question.entities.vo.MultiChoiceVo;

/**
 * 多选题 服务类
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
public interface IMultiChoiceService extends IService<MultiChoice> {

    /**
     * 创建多选题
     * @param req 多选题内容
     * @return 多选题对象
     */
    MultiChoiceVo insert(CreateMultiChoiceReq req);

    /**
     * 根据id查询多选题
     * @param id 多选题id
     * @return 返回视图层对象
     */
    MultiChoiceVo findVoById(Integer id);

    /**
     * 更新多选题
     * @param req 多选题内容
     * @return 多选题对象
     */
    MultiChoiceVo update(UpdateMultiChoiceReq req);

    /**
     * 更新多选题
     * @param id 多选题id
     * @return 是否删除成功
     */
    Boolean delete(Integer id);

    /**
     * 创建报名问卷多选题
     * @param req 多选题内容
     * @return 报名问卷多选题对象
     */
    ApplyChoiceVo insertApply(CreateApplyChoiceReq req);

    /**
     * 根据id查询报名问卷多选题
     * @param id 多选题id
     * @return 报名问卷多选题对象
     */
    ApplyChoiceVo findApplyVoById(Integer id);
}
