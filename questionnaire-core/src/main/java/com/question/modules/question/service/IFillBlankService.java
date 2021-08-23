package com.question.modules.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.FillBlank;
import com.question.modules.question.entities.req.CreateFillBlankReq;
import com.question.modules.question.entities.req.UpdateFillBlankReq;

/**
 * 填空题 服务类
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
public interface IFillBlankService extends IService<FillBlank> {


    /**
     * 根据id查询填空题详细信息
     * @param id 填空题
     * @return 填空题信息
     */
    FillBlank findById(Integer id);

    /**
     * 创建填空题
     * @param req 填空题请求对象
     * @return 填空题内容
     */
    FillBlank insert(CreateFillBlankReq req);

    /**
     * 更新填空题
     * @param req 请求对象
     * @return 填空题信息
     */
    FillBlank update(UpdateFillBlankReq req);
}
