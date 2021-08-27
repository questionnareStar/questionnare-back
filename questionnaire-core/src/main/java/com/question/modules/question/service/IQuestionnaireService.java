package com.question.modules.question.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.entities.apply.vo.QuestionnaireSimpleVo;
import com.question.modules.question.entities.req.CreateQuestionnaireReq;
import com.question.modules.question.entities.req.FillInQuestionnaireReq;
import com.question.modules.question.entities.req.QueryQuestionnairePageReq;
import com.question.modules.question.entities.req.UpdateQuestionnaireReq;
import com.question.modules.question.entities.vo.AnswerVo;
import com.question.modules.question.entities.vo.QuestionnaireDetailVo;

import java.util.List;

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
     * @param type 问卷权限
     */
    Questionnaire createQuestionnaire(CreateQuestionnaireReq req,Integer type);

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
     * @param id 问卷id
     * @return true
     */
    boolean restoreById(String id);

    /**
     * 开启问卷
     * @param id 问卷id
     * @return true
     */
    boolean open(String id);

    /**
     * 关闭问卷
     * @param id 问卷id
     * @return true
     */
    boolean close(String id);

    /**
     * 复制问卷
     * @param id 问卷id
     * @param delete 原问卷 0不删除 1放入回收站
     * @return 问卷基本信息
     */
    Questionnaire copyQuestion(String id, Integer delete);

    /**
     * 查询问卷详情
     * @param id 问卷id
     * @return 问卷详细信息，题目
     */
    QuestionnaireDetailVo detailQuestion(String id);

    /**
     * 填写问卷
     * @param code 问卷密钥
     * @param reqs 答案集合
     * @return true
     */
    boolean fillIn(List<FillInQuestionnaireReq> reqs,String code);

    /**
     * 创建问卷邀请码方式
     * @param req 内容
     * @return 问卷信息
     */
    Questionnaire createQuestionnaireCode(CreateQuestionnaireReq req);

    /**
     * 获取问卷答案
     * @param id 问卷id
     * @return 答案
     */
    List<AnswerVo> getAnswer(String id);

    /**
     * 修改问卷
     * @param req 问卷信息
     * @return 问卷信息
     */
    Questionnaire updateQuestionnaire(UpdateQuestionnaireReq req);

    /**
     * 校验用户是否具有填写问卷的资格
     * @param code 邀请密钥
     * @return true
     */
    boolean fillInIsFlag(String code);

    /**
     * 修改邀请码
     * @param id 问卷id
     * @return 问卷信息
     */
    Questionnaire updateCode(String id);

    /**
     * 修改问卷权限
     * @param id 问卷id
     * @param type 权限
     * @return
     */
    Questionnaire updateType(String id, Integer type);

    /**
     * 查询问卷详情（简易版）
     * @param code 问卷邀请码
     * @return
     */
    QuestionnaireSimpleVo detailApplyQuestion(String code);
}
