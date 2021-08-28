package com.question.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.exam.entities.ExamSingle;
import com.question.modules.exam.entities.req.CreateExamSingleReq;
import com.question.modules.exam.entities.req.UpdateExamSingleReq;
import com.question.modules.exam.entities.vo.ExamSingleVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
public interface IExamSingleService extends IService<ExamSingle> {
    ExamSingleVo create(CreateExamSingleReq req);

    ExamSingleVo updateSingle(UpdateExamSingleReq req);

    Boolean deleteSingleById(Integer id);
}
