package com.question.modules.exam.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.exam.entities.ExamMulti;
import com.question.modules.exam.entities.req.CreateExamMultiReq;
import com.question.modules.exam.entities.req.UpdateExamMultiReq;
import com.question.modules.exam.entities.vo.ExamMultiVo;
import com.question.modules.exam.mapper.ExamMultiMapper;
import com.question.modules.exam.service.IExamMultiService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Service
public class ExamMultiServiceImpl extends ServiceImpl<ExamMultiMapper, ExamMulti> implements IExamMultiService {

    @Override
    public ExamMultiVo create(CreateExamMultiReq req) {
        ExamMulti multi = new ExamMulti();
        multi.setQuestion(req.getQuestion());
        multi.setChoices(JSON.toJSONString(req.getChoices()));
        multi.setAnswer(JSON.toJSONString(req.getAnswer()));
        multi.setRequired(req.getRequired());
        multi.setScore(req.getScore());
        multi.setIsDeleted(false);
        multi.setDesc(req.getDesc());

        save(multi);
        ExamMultiVo multiVo = new ExamMultiVo();
        multiVo.setQuestion(multi.getQuestion());
        multiVo.setChoices(multi.getChoices());
        multiVo.setAnswer(JSON.parseObject(multi.getAnswer(), List.class));
        multiVo.setRequired(multi.getRequired());
        multiVo.setScore(multi.getScore());
        multiVo.setId(multi.getId());
        return multiVo;
    }

    @Override
    public ExamMultiVo updateMulti(UpdateExamMultiReq req) {
        ExamMulti multi = baseMapper.selectById(req.getId());
        multi.setQuestion(req.getQuestion());
        multi.setChoices(JSON.toJSONString(req.getChoices()));
        multi.setAnswer(JSON.toJSONString(req.getAnswer()));
        multi.setRequired(req.getRequired());
        multi.setScore(req.getScore());
        multi.setDesc(req.getDesc());
        updateById(multi);

        ExamMultiVo multiVo = new ExamMultiVo();
        multiVo.setQuestion(multi.getQuestion());
        multiVo.setChoices(multi.getChoices());
        multiVo.setAnswer(JSON.parseObject(multi.getAnswer(), List.class));
        multiVo.setRequired(multi.getRequired());
        multiVo.setScore(multi.getScore());
        multiVo.setId(multi.getId());
        return multiVo;
    }

    @Override
    public Boolean deleteSingleById(Integer id) {
        ExamMulti multi = getBaseMapper().selectById(id);
        if (null == multi) {
            return false;
        }
        multi.setIsDeleted(true);
        return true;
    }
}
