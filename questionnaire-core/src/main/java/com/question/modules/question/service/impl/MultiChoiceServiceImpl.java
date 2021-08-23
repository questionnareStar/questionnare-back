package com.question.modules.question.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.question.entities.MultiChoice;
import com.question.modules.question.entities.req.CreateMultiChoiceReq;
import com.question.modules.question.entities.req.UpdateMultiChoiceReq;
import com.question.modules.question.entities.vo.MultiChoiceVo;
import com.question.modules.question.mapper.MultiChoiceMapper;
import com.question.modules.question.service.IMultiChoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 多选题 服务实现类
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Service
public class MultiChoiceServiceImpl extends ServiceImpl<MultiChoiceMapper, MultiChoice> implements IMultiChoiceService {

    @Override
    public MultiChoiceVo insert(CreateMultiChoiceReq req) {
        MultiChoice multiChoice = new MultiChoice();
        multiChoice.setQuestion(req.getQuestion());
        multiChoice.setChoices(JSON.toJSONString(req.getChoices()));
        multiChoice.setRequired(req.getRequired());
        multiChoice.setIsDeleted(0);
        multiChoice.setDesc(req.getDesc());
        baseMapper.insert(multiChoice);

        MultiChoiceVo vo = new MultiChoiceVo();
        vo.setDesc(multiChoice.getDesc());
        vo.setId(multiChoice.getId());
        vo.setQuestion(multiChoice.getQuestion());
        vo.setChoices(JSON.parseObject(multiChoice.getChoices(),List.class));
        vo.setRequired(multiChoice.getRequired());

        return vo;
    }

    @Override
    public MultiChoiceVo findVoById(Integer id) {
        MultiChoice multiChoice = baseMapper.selectById(id);
        MultiChoiceVo vo = new MultiChoiceVo();
        vo.setId(multiChoice.getId());
        vo.setQuestion(multiChoice.getQuestion());
        vo.setChoices(JSON.parseObject(multiChoice.getChoices(),List.class));
        vo.setRequired(multiChoice.getRequired());
        vo.setDesc(multiChoice.getDesc());

        return vo;
    }

    @Override
    public MultiChoiceVo update(UpdateMultiChoiceReq req) {
        MultiChoice multiChoice = baseMapper.selectById(req.getId());
        multiChoice.setQuestion(req.getQuestion());
        multiChoice.setChoices(JSON.toJSONString(req.getChoices()));
        multiChoice.setRequired(req.getRequired());
        multiChoice.setDesc(req.getDesc());
        baseMapper.updateById(multiChoice);

        MultiChoiceVo vo = new MultiChoiceVo();
        vo.setDesc(multiChoice.getDesc());
        vo.setId(multiChoice.getId());
        vo.setQuestion(multiChoice.getQuestion());
        vo.setChoices(JSON.parseObject(multiChoice.getChoices(),List.class));
        vo.setRequired(multiChoice.getRequired());
        return vo;
    }
}
