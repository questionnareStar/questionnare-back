package com.question.modules.question.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.exception.DefaultException;
import com.question.modules.question.entities.MultiChoice;
import com.question.modules.question.entities.apply.ApplyOptions;
import com.question.modules.question.entities.apply.req.CreateApplyChoiceReq;
import com.question.modules.question.entities.apply.vo.ApplyChoiceVo;
import com.question.modules.question.entities.req.CreateMultiChoiceReq;
import com.question.modules.question.entities.req.UpdateMultiChoiceReq;
import com.question.modules.question.entities.vo.MultiChoiceVo;
import com.question.modules.question.mapper.ApplyOptionsMapper;
import com.question.modules.question.mapper.MultiChoiceMapper;
import com.question.modules.question.service.IMultiChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 多选题 服务实现类
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Service
public class MultiChoiceServiceImpl extends ServiceImpl<MultiChoiceMapper, MultiChoice> implements IMultiChoiceService {


    @Autowired
    private ApplyOptionsMapper applyOptionsMapper;

    @Override
    public MultiChoiceVo insert(CreateMultiChoiceReq req) {
        MultiChoice multiChoice = new MultiChoice();
        multiChoice.setQuestion(req.getQuestion());
        multiChoice.setChoices(JSON.toJSONString(req.getChoices()));
        multiChoice.setRequired(req.getRequired());
        multiChoice.setIsDeleted(false);
        multiChoice.setDesc(req.getDesc());
        baseMapper.insert(multiChoice);


        MultiChoiceVo vo = new MultiChoiceVo();
        vo.setDesc(multiChoice.getDesc());
        vo.setId(multiChoice.getId());
        vo.setQuestion(multiChoice.getQuestion());
        vo.setChoices(JSON.parseObject(multiChoice.getChoices(), List.class));
        vo.setRequired(multiChoice.getRequired());

        return vo;
    }

    @Override
    public MultiChoiceVo findVoById(Integer id) {
        MultiChoice multiChoice = baseMapper.selectById(id);
        MultiChoiceVo vo = new MultiChoiceVo();
        vo.setId(multiChoice.getId());
        vo.setQuestion(multiChoice.getQuestion());
        vo.setChoices(JSON.parseObject(multiChoice.getChoices(), List.class));
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
        vo.setChoices(JSON.parseObject(multiChoice.getChoices(), List.class));
        vo.setRequired(multiChoice.getRequired());
        return vo;
    }

    @Override
    public Boolean delete(Integer id) {
        MultiChoice multiChoice = baseMapper.selectById(id);
        if (multiChoice == null) {
            throw new DefaultException("不存在该题目");
        }
        if (multiChoice.getIsDeleted().equals(true)) {
            throw new DefaultException("该题目已删除");
        }
        multiChoice.setIsDeleted(true);
        updateById(multiChoice);
        return true;
    }

    @Override
    public ApplyChoiceVo insertApply(CreateApplyChoiceReq req) {
        MultiChoice multiChoice = new MultiChoice();
        // 填充基本信息
        multiChoice.setQuestion(req.getQuestion());
        multiChoice.setRequired(req.getRequired());
        multiChoice.setIsDeleted(false);
        multiChoice.setDesc(req.getDesc());

        // 遍历保存选项信息
        ArrayList<Integer> choiceList = new ArrayList<>();
        req.getChoices().forEach(amo -> {
            ApplyOptions applyOptions = new ApplyOptions();
            applyOptions.setName(amo.getName());
            applyOptions.setNumber(amo.getNumber());
            applyOptions.setRemark(amo.getRemark());
            applyOptions.setSelected(0);
            applyOptionsMapper.insert(applyOptions);
            choiceList.add(applyOptions.getId());
        });
        multiChoice.setChoices(JSON.toJSONString(choiceList));
        baseMapper.insert(multiChoice);

        ApplyChoiceVo vo = new ApplyChoiceVo();
        vo.setId(multiChoice.getId());
        vo.setQuestion(multiChoice.getQuestion());
        vo.setDesc(multiChoice.getDesc());
        vo.setRequired(multiChoice.getRequired());
        List<Integer> choices = JSON.parseObject(multiChoice.getChoices(), List.class);
        List<ApplyOptions> choiceResultList = new ArrayList<>();
        choices.forEach(id -> choiceResultList.add(applyOptionsMapper.selectById(id)));
        vo.setChoices(choiceResultList);
        return vo;
    }

    @Override
    public ApplyChoiceVo findApplyVoById(Integer id) {
        MultiChoice multiChoice = baseMapper.selectById(id);

        ApplyChoiceVo vo = new ApplyChoiceVo();
        vo.setId(multiChoice.getId());
        vo.setQuestion(multiChoice.getQuestion());
        vo.setDesc(multiChoice.getDesc());
        vo.setRequired(multiChoice.getRequired());
        // 获取选项内容id
        List<Integer> choices = JSON.parseObject(multiChoice.getChoices(), List.class);

        List<ApplyOptions> choiceResultList = new ArrayList<>();
        choices.forEach(id1 -> choiceResultList.add(applyOptionsMapper.selectById(id1)));
        vo.setChoices(choiceResultList);
        return vo;
    }
}
