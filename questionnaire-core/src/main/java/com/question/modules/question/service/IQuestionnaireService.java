package com.question.modules.question.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.entities.req.CreateQuestionnaireReq;
import com.question.modules.question.entities.req.QueryQuestionnairePageReq;

/**
 * 问卷表 服务类
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
public interface IQuestionnaireService extends IService<Questionnaire> {

    /**
     * 创建问卷
     * @param req 问卷信息
     * @return 问卷id
     */
    Questionnaire createQuestionnaire(CreateQuestionnaireReq req);

    /**
     * 查询我的问卷列表
     * @param req 查询信息
     * @return 问卷集合
     */
    IPage<Questionnaire> getQuestionnaireList(QueryQuestionnairePageReq req);

    /**
     * 查询我的问卷回收站列表
     * @param req 查询信息
     * @return 问卷集合
     */
    IPage<Questionnaire> getQuestionnaireListOld(QueryQuestionnairePageReq req);

    /**
     * 逻辑删除问卷信息
     * @param id 问卷id
     * @return true
     */
    boolean deleteById(String id);

    /**
     * 物理删除问卷信息
     * @param id 问卷id
     * @return true
     */
    boolean deathById(String id);

    /**
     * 从回收站恢复问卷
     * @param id
     * @return
     */
    boolean restoreById(String id);
}
