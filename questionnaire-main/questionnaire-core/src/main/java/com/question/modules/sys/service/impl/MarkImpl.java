package com.question.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.sys.entity.Mark;
import com.question.modules.sys.mapper.MarkMapper;
import com.question.modules.sys.service.IMarkService;
import org.springframework.stereotype.Service;

@Service
public class MarkImpl  extends ServiceImpl<MarkMapper, Mark> implements IMarkService {
    @Override
    public Integer InsertMark(Mark mark) {
        return baseMapper.insert(mark);
    }
}
