package com.question.modules.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.question.entities.MultiChoiceAnswer;
import com.question.modules.question.mapper.MultiChoiceAnswerMapper;
import com.question.modules.question.service.IMultiChoiceAnswerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 多选题回答 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Service
public class MultiChoiceAnswerServiceImpl extends ServiceImpl<MultiChoiceAnswerMapper, MultiChoiceAnswer> implements IMultiChoiceAnswerService {

}
