package com.question.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.exam.entities.ExamMulti;
import com.question.modules.exam.entities.req.CreateExamMultiReq;
import com.question.modules.exam.entities.req.UpdateExamMultiReq;
import com.question.modules.exam.entities.vo.ExamMultiVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
public interface IExamMultiService extends IService<ExamMulti> {
    ExamMultiVo create(CreateExamMultiReq req);

    ExamMultiVo updateMulti(UpdateExamMultiReq req);

    Boolean deleteSingleById(Integer id);
}
