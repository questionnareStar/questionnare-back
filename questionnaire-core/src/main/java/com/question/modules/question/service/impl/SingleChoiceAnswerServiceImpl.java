package com.question.modules.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.modules.question.entities.SingleChoiceAnswer;
import com.question.modules.question.mapper.SingleChoiceAnswerMapper;
import com.question.modules.question.service.ISingleChoiceAnswerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 单选题回答 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Service
public class SingleChoiceAnswerServiceImpl extends ServiceImpl<SingleChoiceAnswerMapper, SingleChoiceAnswer> implements ISingleChoiceAnswerService {

}
