package com.question.modules.question.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.exception.DefaultException;
import com.question.modules.question.entities.FillBlank;
import com.question.modules.question.entities.req.CreateFillBlankReq;
import com.question.modules.question.entities.req.UpdateFillBlankReq;
import com.question.modules.question.mapper.FillBlankMapper;
import com.question.modules.question.service.IFillBlankService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 填空题 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Service
public class FillBlankServiceImpl extends ServiceImpl<FillBlankMapper, FillBlank> implements IFillBlankService {

    @Override
    public FillBlank findById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public FillBlank insert(CreateFillBlankReq req) {
        FillBlank fillBlank = new FillBlank();
        fillBlank.setQuestion(req.getQuestion());
        fillBlank.setRequired(req.isRequired());
        fillBlank.setDesc(req.getDesc());
        fillBlank.setIsDeleted(false);
        baseMapper.insert(fillBlank);
        return fillBlank;
    }

    @Override
    public FillBlank update(UpdateFillBlankReq req) {
        FillBlank fillBlank = baseMapper.selectById(req.getId());
        fillBlank.setQuestion(req.getQuestion());
        fillBlank.setRequired(req.isRequired());
        fillBlank.setDesc(req.getDesc());
        baseMapper.updateById(fillBlank);
        return fillBlank;
    }

    @Override
    public Boolean delete(Integer id) {
        FillBlank fillBlank = baseMapper.selectById(id);
        if(fillBlank==null){
            throw new DefaultException("不存在该题目");
        }
        if(fillBlank.getIsDeleted().equals(true)){
            throw new DefaultException("该题目已删除");
        }
        fillBlank.setIsDeleted(true);
        updateById(fillBlank);
        return true;
    }
}
