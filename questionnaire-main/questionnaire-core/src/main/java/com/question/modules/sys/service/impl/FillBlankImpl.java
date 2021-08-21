package com.question.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.sys.entity.FillBlank;
import com.question.modules.sys.mapper.FillBlankMapper;
import com.question.modules.sys.service.IFillBlankService;
import org.springframework.stereotype.Service;

@Service
public class FillBlankImpl extends ServiceImpl<FillBlankMapper, FillBlank> implements IFillBlankService {
    @Override
    public Integer InsertFillBlank(FillBlank fillBlank) {
        return baseMapper.insert(fillBlank);
    }
}
