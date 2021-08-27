package com.question.modules.exam.service;

import com.question.modules.exam.entities.ExamAnswerRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.exam.entities.req.AnswerExamQuestionReq;
import com.question.modules.exam.entities.vo.ExamQuestionnaireAnswerVo;
import com.question.modules.exam.entities.vo.ExamSingleVo;
import com.question.modules.exam.entities.vo.FeedBackVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
public interface IExamAnswerRecordService extends IService<ExamAnswerRecord> {

    FeedBackVo getAllAnswerRecordByQuestionnaireId(Integer id);

    ExamQuestionnaireAnswerVo getUserScore(Integer user_id, Integer questionnaire_id);

    boolean fillIn(String code, List<AnswerExamQuestionReq> reqs);
}
