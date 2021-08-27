package com.question.modules.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.exam.entities.ExamFillIn;
import com.question.modules.exam.entities.req.CreateExamFillInReq;
import com.question.modules.exam.entities.req.UpdateExamFillInReq;
import com.question.modules.exam.entities.vo.ExamFillInVo;
import com.question.modules.exam.mapper.ExamFillInMapper;
import com.question.modules.exam.service.IExamFillInService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Service
public class ExamFillInServiceImpl extends ServiceImpl<ExamFillInMapper, ExamFillIn> implements IExamFillInService {

    @Override
    public ExamFillInVo create(CreateExamFillInReq req) {
        ExamFillIn fillIn = new ExamFillIn();
        fillIn.setQuestion(req.getQuestion());
        fillIn.setAnswer(req.getAnswer());
        fillIn.setRequired(req.getRequired());
        fillIn.setIsDeleted(false);
        fillIn.setDesc(req.getDesc());
        save(fillIn);
        ExamFillInVo fillInVo = new ExamFillInVo();
        fillInVo.setId(fillIn.getId());
        fillInVo.setQuestion(fillIn.getQuestion());
        fillInVo.setAnswer(fillIn.getAnswer());
        fillInVo.setRequired(fillIn.getRequired());
        fillInVo.setDesc(fillIn.getDesc());
        return fillInVo;
    }

    @Override
    public ExamFillInVo updateFillIn(UpdateExamFillInReq req) {
        ExamFillIn fillIn = getById(req.getId());
        fillIn.setQuestion(req.getQuestion());
        fillIn.setAnswer(req.getAnswer());
        fillIn.setRequired(req.getRequired());
        fillIn.setDesc(req.getDesc());
        updateById(fillIn);
        ExamFillInVo fillInVo = new ExamFillInVo();
        fillInVo.setId(fillIn.getId());
        fillInVo.setQuestion(fillIn.getQuestion());
        fillInVo.setAnswer(fillIn.getAnswer());
        fillInVo.setRequired(fillIn.getRequired());
        fillInVo.setDesc(fillIn.getDesc());
        return fillInVo;
    }

    @Override
    public Boolean deleteSingleById(Integer id) {
        ExamFillIn fillIn = baseMapper.selectById(id);
        if (null == fillIn) {
            return false;
        }
        fillIn.setIsDeleted(true);
        updateById(fillIn);
        return true;
    }
}
