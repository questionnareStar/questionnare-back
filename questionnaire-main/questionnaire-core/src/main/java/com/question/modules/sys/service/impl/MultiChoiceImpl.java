package com.question.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.sys.entity.MultiChoice;
import com.question.modules.sys.mapper.MultiChoiceMapper;
import com.question.modules.sys.service.IMultiChoiceService;
import org.springframework.stereotype.Service;

@Service
public class MultiChoiceImpl  extends ServiceImpl<MultiChoiceMapper, MultiChoice> implements IMultiChoiceService {
    @Override
    public Integer InsertMultiChoice(MultiChoice multiChoice) {
        return baseMapper.insert(multiChoice);
    }
}
