package com.question.modules.exam.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.exam.entities.ExamSingle;
import com.question.modules.exam.entities.req.CreateExamSingleReq;
import com.question.modules.exam.entities.req.UpdateExamSingleReq;
import com.question.modules.exam.entities.vo.ExamSingleVo;
import com.question.modules.exam.mapper.ExamSingleMapper;
import com.question.modules.exam.service.IExamSingleService;
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
public class ExamSingleServiceImpl extends ServiceImpl<ExamSingleMapper, ExamSingle> implements IExamSingleService {

    @Override
    public ExamSingleVo create(CreateExamSingleReq req) {
        ExamSingle single = new ExamSingle();
        single.setChoices(JSON.toJSONString(req.getChoices()));
        single.setQuestion(req.getQuestion());
        single.setAnswer(req.getAnswer());
        single.setRequired(req.getRequired());
        single.setDesc(req.getDesc());
        single.setScore(req.getScore());
        single.setIsDeleted(false);
        save(single);
        ExamSingleVo examSingleVo = new ExamSingleVo();
        examSingleVo.setQuestion(single.getQuestion());
        examSingleVo.setChoices(single.getChoices());
        examSingleVo.setAnswer(single.getAnswer());
        examSingleVo.setRequired(single.getRequired());
        examSingleVo.setDesc(single.getDesc());
        examSingleVo.setId(single.getId());
        examSingleVo.setScore(single.getScore());
        return examSingleVo;
    }

    @Override
    public ExamSingleVo updateSingle(UpdateExamSingleReq req) {
        ExamSingle single = baseMapper.selectById(req.getId());
        single.setChoices(JSON.toJSONString(req.getChoices()));
        single.setQuestion(req.getQuestion());
        single.setAnswer(req.getAnswer());
        single.setRequired(req.getRequired());
        single.setDesc(req.getDesc());
        single.setScore(req.getScore());
        updateById(single);

        ExamSingleVo examSingleVo = new ExamSingleVo();
        examSingleVo.setQuestion(single.getQuestion());
        examSingleVo.setChoices(single.getChoices());
        examSingleVo.setAnswer(single.getAnswer());
        examSingleVo.setRequired(single.getRequired());
        examSingleVo.setDesc(single.getDesc());
        examSingleVo.setId(single.getId());
        examSingleVo.setScore(single.getScore());
        return examSingleVo;

    }

    @Override
    public Boolean deleteSingleById(Integer id) {
        ExamSingle single = baseMapper.selectById(id);
        if (single == null) {
            return false;
        }
        single.setIsDeleted(true);
        updateById(single);
        return true;
    }
}
