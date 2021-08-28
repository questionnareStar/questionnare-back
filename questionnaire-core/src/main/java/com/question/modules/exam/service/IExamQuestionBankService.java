package com.question.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.exam.entities.ExamQuestionBank;
import com.question.modules.exam.entities.vo.ExamQuestionnaireDetailVo;
import com.question.modules.exam.entities.vo.statistics.ExamStatisticsVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
public interface IExamQuestionBankService extends IService<ExamQuestionBank> {

    ExamQuestionnaireDetailVo getDetailedQuestionnaire(String code);

    ExamStatisticsVo getQuestionnaireStatistics(String id);

    List<ExamQuestionBank> getExamQuestionBankListByQuestionnaireId(Integer id);
}
