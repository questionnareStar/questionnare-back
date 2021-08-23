package com.question.modules.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.MultiChoice;
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
}
