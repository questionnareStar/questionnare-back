package com.question.modules.exam.service;

import com.question.modules.exam.entities.ExamFillIn;
import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.exam.entities.req.CreateExamFillInReq;
import com.question.modules.exam.entities.req.UpdateExamFillInReq;
import com.question.modules.exam.entities.vo.ExamFillInVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
public interface IExamFillInService extends IService<ExamFillIn> {
    ExamFillInVo create(CreateExamFillInReq req);
    ExamFillInVo updateFillIn(UpdateExamFillInReq req);

    Boolean deleteSingleById(Integer id);
}
