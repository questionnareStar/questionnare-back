package com.question.modules.question.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.QuestionBank;

import java.util.List;

/**
 * 题库表 服务类
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
public interface IQuestionBankService extends IService<QuestionBank> {

    /**
     * 根据问卷id，查询题库集合
     * @param questionnaireId 问卷id
     * @return 题库集合
     */
    List<QuestionBank> findByQuestionId(Integer questionnaireId);

    /**
     * 根据问卷id清空题库信息
     * @param id 问卷id
     */
    void deleteByQuestionnaireId(Integer id);

    /**
     * 获取问卷填写数据
     * @param questionnaireId 问卷id
     * @return 问卷数据
     */
    JSONObject createQuestionnaireStatic(Integer questionnaireId);
}
