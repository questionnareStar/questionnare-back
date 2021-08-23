package com.question.modules.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.question.entities.Mark;
import com.question.modules.question.entities.req.CreateMarkReq;
import com.question.modules.question.entities.req.UpdateMarkReq;
import com.question.modules.question.mapper.MarkMapper;
import com.question.modules.question.service.IMarkService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评分题 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Service
public class MarkServiceImpl extends ServiceImpl<MarkMapper, Mark> implements IMarkService {

    @Override
    public Mark insert(CreateMarkReq req) {
        Mark mark = new Mark();
        mark.setQuestion(req.getQuestion());
        mark.setMaxScore(req.getMaxScore());
        mark.setRequired(req.getRequired());
        mark.setDesc(req.getDesc());
        mark.setIsDeleted(0);
        baseMapper.insert(mark);
        return mark;
    }

    @Override
    public Mark findById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Mark update(UpdateMarkReq req) {
        Mark mark = baseMapper.selectById(req.getId());
        mark.setQuestion(req.getQuestion());
        mark.setMaxScore(req.getMaxScore());
        mark.setRequired(req.getRequired());
        mark.setDesc(req.getDesc());

        baseMapper.updateById(mark);
        return mark;
    }
}
