package com.question.modules.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.Mark;
import com.question.modules.question.entities.req.CreateMarkReq;
import com.question.modules.question.entities.req.UpdateMarkReq;

/**
 * <p>
 * 评分题 服务类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
public interface IMarkService extends IService<Mark> {

    /**
     * 创建评分题
     *
     * @param req 评分提信息
     * @return 评分题
     */
    Mark insert(CreateMarkReq req);

    /**
     * 根据id查询评分题目内容
     *
     * @param id 评分题id
     * @return 评分题内容
     */
    Mark findById(Integer id);

    /**
     * 更新评分题
     *
     * @param req 评分题信息
     * @return 评分题信息
     */
    Mark update(UpdateMarkReq req);

    /**
     * 更新评分题
     *
     * @param id 多选题id
     * @return 是否删除成功
     */
    Boolean delete(Integer id);
}
