package com.question.modules.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.question.modules.question.entities.UserAnswer;
import com.question.modules.question.entities.vo.AnswerUserVo;

import java.util.List;

/**
 * 记录用户填写的信息 服务类
 *
 * @author 问卷星球团队
 * @since 2021-08-27
 */
public interface IUserAnswerService extends IService<UserAnswer> {


    /**
     * 返回问卷的答案
     * @param code 问卷码
     * @return
     */
    List<List<AnswerUserVo>> findQuestionAnswer(String code);
}
