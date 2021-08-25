package com.question.modules.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.AnswerRecord;
import com.question.modules.question.entities.vo.AnswerNumberVo;

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

}
