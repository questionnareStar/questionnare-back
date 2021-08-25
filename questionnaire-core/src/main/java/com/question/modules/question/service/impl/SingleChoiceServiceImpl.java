package com.question.modules.question.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.exception.DefaultException;
import com.question.modules.question.entities.SingleChoice;
import com.question.modules.question.entities.req.CreateSingleChoiceReq;
import com.question.modules.question.entities.req.UpdateSingleChoiceReq;
import com.question.modules.question.entities.vo.SingleChoiceVo;
import com.question.modules.question.mapper.SingleChoiceMapper;
import com.question.modules.question.service.ISingleChoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 单选题 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Service
public class SingleChoiceServiceImpl extends ServiceImpl<SingleChoiceMapper, SingleChoice> implements ISingleChoiceService {

    @Override
    public SingleChoiceVo insert(CreateSingleChoiceReq req) {
        SingleChoice singleChoice = new SingleChoice();
        singleChoice.setDesc(req.getDesc());
        singleChoice.setQuestion(req.getQuestion());
        singleChoice.setChoices(JSON.toJSONString(req.getChoices()));
        singleChoice.setRequired(req.getRequired());
        singleChoice.setIsDeleted(false);
        baseMapper.insert(singleChoice);

        SingleChoiceVo singleChoiceVo = new SingleChoiceVo();
        singleChoiceVo.setDesc(singleChoice.getDesc());
        singleChoiceVo.setId(singleChoice.getId());
        singleChoiceVo.setQuestion(singleChoice.getQuestion());
        singleChoiceVo.setChoices(JSON.parseObject(singleChoice.getChoices(), List.class));
        singleChoiceVo.setRequired(singleChoice.getRequired());

        return singleChoiceVo;
    }

    @Override
    public SingleChoiceVo findVoById(Integer id) {

        SingleChoice singleChoice = baseMapper.selectById(id);

        SingleChoiceVo singleChoiceVo = new SingleChoiceVo();
        singleChoiceVo.setDesc(singleChoice.getDesc());
        singleChoiceVo.setId(singleChoice.getId());
        singleChoiceVo.setQuestion(singleChoice.getQuestion());
        singleChoiceVo.setChoices(JSON.parseObject(singleChoice.getChoices(), List.class));
        singleChoiceVo.setRequired(singleChoice.getRequired());
        return singleChoiceVo;
    }

    @Override
    public SingleChoiceVo update(UpdateSingleChoiceReq req) {
        SingleChoice singleChoice = baseMapper.selectById(req.getId());
        singleChoice.setDesc(req.getDesc());
        singleChoice.setQuestion(req.getQuestion());
        singleChoice.setChoices(JSON.toJSONString(req.getChoices()));
        singleChoice.setRequired(req.getRequired());
        singleChoice.setDesc(req.getDesc());
        baseMapper.updateById(singleChoice);

        SingleChoiceVo singleChoiceVo = new SingleChoiceVo();
        singleChoiceVo.setDesc(singleChoice.getDesc());
        singleChoiceVo.setId(singleChoice.getId());
        singleChoiceVo.setQuestion(singleChoice.getQuestion());
        singleChoiceVo.setChoices(JSON.parseObject(singleChoice.getChoices(), List.class));
        singleChoiceVo.setRequired(singleChoice.getRequired());

        return singleChoiceVo;
    }

    @Override
    public Boolean delete(Integer id) {
        SingleChoice singleChoice = baseMapper.selectById(id);
        if (singleChoice == null) {
            throw new DefaultException("不存在该题目");
        }
        if (singleChoice.getIsDeleted().equals(true)) {
            throw new DefaultException("该题目已删除");
        }
        singleChoice.setIsDeleted(true);
        updateById(singleChoice);
        return true;
    }
}
