package com.question.modules.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.AnswerRecord;
import com.question.modules.question.entities.vo.AnswerNumberVo;
import com.question.modules.question.entities.vo.QuestionnaireAnswerVo;
import com.question.modules.question.entities.vo.QuestionsCrossAnalysisVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-25
 */
public interface IAnswerRecordService extends IService<AnswerRecord> {

    AnswerNumberVo createQuestionnaireAnswerNumber(Integer questionnaireId);

    QuestionnaireAnswerVo getAnswerRecordDetailInfo(Integer questionnaireId, Integer record_id);

    QuestionsCrossAnalysisVo getCrossAnalysisResult(Integer questionnaireId, Integer type1, Integer question1Id, Integer type2, Integer question2Id);
}
