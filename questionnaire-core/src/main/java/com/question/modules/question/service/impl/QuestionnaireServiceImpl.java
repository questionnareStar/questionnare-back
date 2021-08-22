package com.question.modules.question.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.exception.DefaultException;
import com.question.modules.question.entities.QuestionBank;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.entities.req.CreateQuestionnaireReq;
import com.question.modules.question.entities.req.HeadingItemReq;
import com.question.modules.question.entities.req.QueryQuestionnairePageReq;
import com.question.modules.question.entities.vo.FillBlankVo;
import com.question.modules.question.entities.vo.QuestionnaireDetailVo;
import com.question.modules.question.mapper.QuestionnaireMapper;
import com.question.modules.question.service.IQuestionBankService;
import com.question.modules.question.service.IQuestionnaireService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 问卷表 服务实现类
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Service
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements IQuestionnaireService {

    @Autowired
    private IQuestionBankService questionBankService;

    @Override
    public Questionnaire createQuestionnaire(CreateQuestionnaireReq req) {
        int userId = StpUtil.getLoginIdAsInt();
        // 填入问卷信息
        Questionnaire questionnaire = saveQuestionnaire(req, userId);
        // 拆解问卷问题，保存问卷信息到题库表
        saveQuestionBank(req.getItemList(), questionnaire);
        return questionnaire;
    }


    @Override
    public IPage<Questionnaire> getQuestionnaireList(QueryQuestionnairePageReq req) {
        // 获取封装后的查询条件
        QueryWrapper<Questionnaire> wrapper = getPageWrapper(req);
        wrapper.eq("is_deleted", 0);
        return baseMapper.selectPage(req.getPage(), wrapper);
    }


    @Override
    public IPage<Questionnaire> getQuestionnaireListOld(QueryQuestionnairePageReq req) {
        // 获取封装后的查询条件
        QueryWrapper<Questionnaire> wrapper = getPageWrapper(req);
        wrapper.eq("is_deleted", 1);
        return baseMapper.selectPage(req.getPage(), wrapper);
    }


    @Override
    public boolean deleteById(String id) {
        int userId = StpUtil.getLoginIdAsInt();
        Questionnaire questionnaire = baseMapper.selectById(id);
        if (!questionnaire.getUserId().equals(userId)) {
            throw new DefaultException("您没有删除问卷的权限，只有问卷的创建者才能删除");
        }
        questionnaire.setDeleted(true);
        baseMapper.updateById(questionnaire);
        return true;
    }


    @Override
    public boolean deathById(String id) {
        int userId = StpUtil.getLoginIdAsInt();
        Questionnaire questionnaire = baseMapper.selectById(id);
        if (!questionnaire.getUserId().equals(userId)) {
            throw new DefaultException("您没有删除问卷的权限，只有问卷的创建者才能删除");
        }
        baseMapper.deleteById(id);
        return true;
    }


    @Override
    public boolean restoreById(String id) {
        int userId = StpUtil.getLoginIdAsInt();
        Questionnaire questionnaire = baseMapper.selectById(id);
        if (questionnaire==null){
            throw new DefaultException("该问卷不存在，可能已被删除");
        }
        if (!questionnaire.getUserId().equals(userId)) {
            throw new DefaultException("您没有恢复问卷的权限，只有问卷的创建者才能恢复");
        }
        questionnaire.setDeleted(false);
        baseMapper.updateById(questionnaire);
        return true;
    }


    @Override
    public boolean open(String id) {
        int userId = StpUtil.getLoginIdAsInt();
        Questionnaire questionnaire = baseMapper.selectById(id);
        if (!questionnaire.getUserId().equals(userId)) {
            throw new DefaultException("您没有发布问卷的权限，，只有问卷的创建者才能发布");
        }
        if (questionnaire.getIsReleased().equals(1)){
            throw new DefaultException("您的问卷已发布");
        }
        questionnaire.setIsReleased(1);
        baseMapper.updateById(questionnaire);
        return true;
    }


    @Override
    public boolean close(String id) {
        int userId = StpUtil.getLoginIdAsInt();
        Questionnaire questionnaire = baseMapper.selectById(id);
        if (!questionnaire.getUserId().equals(userId)) {
            throw new DefaultException("您没有关闭问卷的权限，，只有问卷的创建者才能关闭");
        }
        if (questionnaire.getIsReleased().equals(0)){
            throw new DefaultException("您的问卷已取消发布");
        }
        questionnaire.setIsReleased(0);
        baseMapper.updateById(questionnaire);
        return true;
    }


    @Override
    public Questionnaire copyQuestion(String id) {

        int userId = StpUtil.getLoginIdAsInt();
        Questionnaire questionnaire = baseMapper.selectById(id);
        // 2.复制基本信息，并保存
        Questionnaire questionNew = questionnaire;
        questionNew.setId(null);
        questionNew.setCreateTime(new Date());
        questionNew.setUserId(userId);
        baseMapper.insert(questionNew);
        // 2.复制题库信息
        List<QuestionBank> bankList = questionBankService.findByQuestionId(questionnaire.getId());
        bankList.forEach(questionBank -> {
            questionBank.setQuestionnaireId(questionNew.getId());
            questionBankService.save(questionBank);
        });
        return questionNew;
    }


    @Override
    public QuestionnaireDetailVo detailQuestion(String id) {
        Questionnaire questionnaire = baseMapper.selectById(id);
        // 获取题库集合
        List<QuestionBank> bankList = questionBankService.findByQuestionId(questionnaire.getId());
        // 存放题库
        List<Object> itemList = new ArrayList<>();
        // 遍历集合
        for (QuestionBank bank : bankList) {
            if (bank.getType().equals(1)){
                // 填空题
                FillBlankVo fillBlankVo = new FillBlankVo();
                fillBlankVo.setTopicId(0);
                fillBlankVo.setQuestion("");
                fillBlankVo.setRequired(false);
                fillBlankVo.setSequence(false);
                fillBlankVo.setType(false);

            }
        }
        return null;
    }


    /**
     * 保存问题集合
     *
     * @param itemList 问题集合
     */
    private void saveQuestionBank(List<HeadingItemReq> itemList, Questionnaire questionnaire) {
        itemList.forEach(headingItem -> {
            QuestionBank questionBank = new QuestionBank();
            questionBank.setType(headingItem.getItemType());
            questionBank.setTopicId(headingItem.getItemId());
            questionBank.setQuestionnaireId(questionnaire.getId());
            questionBank.setSequence(headingItem.getItemOrder());
            questionBankService.save(questionBank);
        });
    }


    /**
     * 根据用户的查询数据，查询的条件
     * @param req 查询条件
     * @return
     */
    private QueryWrapper<Questionnaire> getPageWrapper(QueryQuestionnairePageReq req) {
        int userId = StpUtil.getLoginIdAsInt();
        // 编辑查询条件
        QueryWrapper<Questionnaire> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(req.getHead())) {
            wrapper.like("head", req.getHead());
        }
        if (req.getType() != null) {
            wrapper.eq("type", req.getType());
        }
        if (req.getIsReleased() != null) {
            wrapper.eq("is_released", req.getIsReleased());
        }
        if (req.getSortBy() != null) {
            if (req.getSortBy().equals(0)) {
                wrapper.orderByDesc("create_time");
            } else if (req.getSortBy().equals(1)) {
                wrapper.orderByDesc("write_num");
            }else if (req.getSortBy().equals(3)) {
                wrapper.orderByAsc("create_time");
            } else if (req.getSortBy().equals(4)) {
                wrapper.orderByAsc("write_num");
            }
        }
        wrapper.eq("user_id",userId);
        return wrapper;
    }

    /**
     * 保存问卷信息
     *
     * @param req 问卷信息
     */
    private Questionnaire saveQuestionnaire(CreateQuestionnaireReq req, int userId) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setHead(req.getHead());
        questionnaire.setUserId(userId);
        questionnaire.setCreateTime(new Date());
        questionnaire.setIntroduction(req.getIntroduction());
        questionnaire.setType(req.getType());
        questionnaire.setDeleted(false);
        questionnaire.setIsReleased(req.getIsReleased());
        questionnaire.setStartTime(req.getStartTime());
        questionnaire.setEndTime(req.getEndTime());
        questionnaire.setWriteNum(0);
        baseMapper.insert(questionnaire);
        return questionnaire;
    }

}
