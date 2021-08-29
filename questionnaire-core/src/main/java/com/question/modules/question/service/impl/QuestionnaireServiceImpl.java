package com.question.modules.question.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.question.exception.DefaultException;
import com.question.modules.exam.entities.ExamFillIn;
import com.question.modules.exam.entities.ExamMulti;
import com.question.modules.exam.entities.ExamQuestionBank;
import com.question.modules.exam.entities.ExamSingle;
import com.question.modules.exam.mapper.ExamFillInMapper;
import com.question.modules.exam.mapper.ExamMultiMapper;
import com.question.modules.exam.mapper.ExamQuestionBankMapper;
import com.question.modules.exam.mapper.ExamSingleMapper;
import com.question.modules.exam.service.IExamQuestionBankService;
import com.question.modules.question.entities.*;
import com.question.modules.question.entities.apply.vo.ApplyChoiceVo;
import com.question.modules.question.entities.apply.vo.BankVo;
import com.question.modules.question.entities.apply.vo.QuestionnaireSimpleVo;
import com.question.modules.question.entities.req.*;
import com.question.modules.question.entities.vo.*;
import com.question.modules.question.mapper.*;
import com.question.modules.question.service.*;
import com.question.modules.sys.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
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
    private ScheduleService scheduleService;
    /**
     * 题库
     */
    @Autowired
    private IQuestionBankService questionBankService;

    /**
     * 填空题
     */
    @Autowired
    private IFillBlankService fillBlankService;

    /**
     * 评分题
     */
    @Autowired
    private IMarkService markService;

    /**
     * 多选题
     */
    @Autowired
    private IMultiChoiceService multiChoiceService;

    /**
     * 单选题
     */
    @Autowired
    private ISingleChoiceService singleChoiceService;

    /**
     * 用户
     */
    @Autowired
    private IUserService userService;

    /**
     * 用户问答
     */
    @Autowired
    private QuestionnaireUserMapper questionnaireUserMapper;

    /**
     * 填空题答案
     */
    @Autowired
    private FillBlankAnswerMapper fillBlankAnswerMapper;

    /**
     * 评分题答案
     */
    @Autowired
    private MarkAnswerMapper markAnswerMapper;

    /**
     * 多选题答案
     */
    @Autowired
    private MultiChoiceAnswerMapper multiChoiceAnswerMapper;

    /**
     * 单选题答案
     */
    @Autowired
    private SingleChoiceAnswerMapper singleChoiceAnswerMapper;

    /**
     * 回答记录
     */
    @Autowired
    private AnswerRecordMapper answerRecordMapper;


    /**
     * 选项
     */
    @Autowired
    private ApplyOptionsMapper applyOptionsMapper;


    @Autowired
    private UserAnswerMapper userAnswerMapper;

    /**
     * 考试问卷题库
     */
    @Autowired
    private ExamQuestionBankMapper examQuestionBankMapper;

    /**
     * 考试填空题
     */
    @Autowired
    private ExamFillInMapper examFillInMapper;

    /**
     * 考试单选题
     */
    @Autowired
    private ExamSingleMapper examSingleMapper;

    /**
     * 考试问多选题
     */
    @Autowired
    private ExamMultiMapper examMultiMapper;

    /**
     * 考试问卷题库
     */
    @Autowired
    private IExamQuestionBankService examQuestionBankService;


    @Override
    public Questionnaire createQuestionnaire(CreateQuestionnaireReq req, Integer type) {
        int userId = StpUtil.getLoginIdAsInt();
        // 填入问卷信息
        Questionnaire questionnaire = saveQuestionnaire(req, userId, type);
        scheduleService.addQuestionnaireTask(questionnaire.getId());
        // 拆解问卷问题，保存问卷信息到题库表
        if (req.getStamp() != 5) {
            saveQuestionBank(req.getItemList(), questionnaire);
        } else {
            saveExamQuestionBank(req.getItemList(), questionnaire.getId());
        }
        return questionnaire;
    }

    private void saveExamQuestionBank(List<HeadingItemReq> itemList, Integer questionnaireId) {
        for (HeadingItemReq item : itemList) {
            ExamQuestionBank questionBank1 = new ExamQuestionBank();
            questionBank1.setQuestionId(item.getItemId());
            questionBank1.setQuestionnaireId(questionnaireId);
            questionBank1.setType(item.getItemType() - 6);
            questionBank1.setSequence(item.getItemOrder());
            examQuestionBankMapper.insert(questionBank1);
        }
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
        //取消定时任务
        scheduleService.cancelQuestionnaireTask(Integer.parseInt(id));
        baseMapper.deleteById(id);
        return true;
    }


    @Override
    public boolean restoreById(String id) {
        int userId = StpUtil.getLoginIdAsInt();
        Questionnaire questionnaire = baseMapper.selectById(id);
        if (questionnaire == null) {
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
        if (questionnaire.getIsReleased().equals(1)) {
            throw new DefaultException("您的问卷已发布");
        }
        questionnaire.setIsReleased(1);
        questionnaire.setStartTime(new Date());
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
        if (questionnaire.getIsReleased().equals(0)) {
            throw new DefaultException("您的问卷已取消发布");
        }
        questionnaire.setIsReleased(0);
        baseMapper.updateById(questionnaire);
        return true;
    }


    @Override
    public Questionnaire copyQuestion(String id, Integer delete) {
        int userId = StpUtil.getLoginIdAsInt();
        Questionnaire questionnaire = baseMapper.selectById(id);
        // 2.复制基本信息，并保存
        Questionnaire questionNew = new Questionnaire();
        questionNew.setHead(questionnaire.getHead() + "(副本)");
        questionNew.setUserId(userId);
        questionNew.setCreateTime(new Date());
        questionNew.setIntroduction(questionnaire.getIntroduction());
        questionNew.setType(questionnaire.getType());
        questionNew.setDeleted(questionnaire.isDeleted());
        questionNew.setIsReleased(questionnaire.getIsReleased());
        questionNew.setStartTime(questionnaire.getStartTime());
        questionNew.setEndTime(questionnaire.getEndTime());
        questionNew.setWriteNum(0);
        questionNew.setCode(RandomUtil.randomString(6));
        questionNew.setSerial(questionnaire.isSerial());
        baseMapper.insert(questionNew);
        //创建定时任务
        scheduleService.addQuestionnaireTask(questionNew.getId());
        // 2.1复制题库信息 - 非考试问卷
        List<QuestionBank> bankList = questionBankService.findByQuestionId(questionnaire.getId());
        for (QuestionBank questionBank : bankList) {
            switch (questionBank.getType()) {
                case 1:
                    // 复制填空题
                    FillBlank fillBlank = fillBlankService.findById(questionBank.getTopicId());
                    CreateFillBlankReq req = new CreateFillBlankReq();
                    req.setQuestion(fillBlank.getQuestion());
                    req.setRequired(fillBlank.isRequired());
                    req.setDesc(fillBlank.getDesc());
                    FillBlank insert = fillBlankService.insert(req);
                    // 保存到题库
                    QuestionBank bank = new QuestionBank();
                    bank.setType(questionBank.getType());
                    bank.setTopicId(insert.getId());
                    bank.setQuestionnaireId(questionNew.getId());
                    bank.setSequence(questionBank.getSequence());
                    questionBankService.save(bank);
                    break;
                case 2:
                    // 复制评分题
                    Mark mark = markService.findById(questionBank.getTopicId());
                    CreateMarkReq createMarkReq = new CreateMarkReq();
                    createMarkReq.setQuestion(mark.getQuestion());
                    createMarkReq.setMaxScore(mark.getMaxScore());
                    createMarkReq.setRequired(mark.getRequired());
                    createMarkReq.setDesc(mark.getDesc());
                    Mark insert1 = markService.insert(createMarkReq);

                    // 保存到题库
                    QuestionBank bank1 = new QuestionBank();
                    bank1.setType(questionBank.getType());
                    bank1.setTopicId(insert1.getId());
                    bank1.setQuestionnaireId(questionNew.getId());
                    bank1.setSequence(questionBank.getSequence());
                    questionBankService.save(bank1);
                    break;
                case 3:
                case 6:
                    // 复制多选题
                    MultiChoiceVo multiChoiceVo = multiChoiceService.findVoById(questionBank.getTopicId());
                    CreateMultiChoiceReq createMultiChoiceReq = new CreateMultiChoiceReq();
                    createMultiChoiceReq.setQuestion(multiChoiceVo.getQuestion());
                    createMultiChoiceReq.setChoices(multiChoiceVo.getChoices());
                    createMultiChoiceReq.setRequired(multiChoiceVo.getRequired());
                    createMultiChoiceReq.setDesc(multiChoiceVo.getDesc());
                    MultiChoiceVo insert3 = multiChoiceService.insert(createMultiChoiceReq);

                    // 保存到题库
                    QuestionBank bank2 = new QuestionBank();
                    bank2.setType(questionBank.getType());
                    bank2.setTopicId(insert3.getId());
                    bank2.setQuestionnaireId(questionNew.getId());
                    bank2.setSequence(questionBank.getSequence());
                    questionBankService.save(bank2);
                    break;
                case 4:
                case 5:
                    // 复制单选题
                    SingleChoiceVo singleChoiceVo = singleChoiceService.findVoById(questionBank.getTopicId());
                    CreateSingleChoiceReq createSingleChoiceReq = new CreateSingleChoiceReq();
                    createSingleChoiceReq.setQuestion(singleChoiceVo.getQuestion());
                    createSingleChoiceReq.setChoices(singleChoiceVo.getChoices());
                    createSingleChoiceReq.setRequired(singleChoiceVo.getRequired());
                    createSingleChoiceReq.setDesc(singleChoiceVo.getDesc());
                    SingleChoiceVo insert4 = singleChoiceService.insert(createSingleChoiceReq);

                    // 保存到题库
                    QuestionBank bank3 = new QuestionBank();
                    bank3.setType(questionBank.getType());
                    bank3.setTopicId(insert4.getId());
                    bank3.setQuestionnaireId(questionNew.getId());
                    bank3.setSequence(questionBank.getSequence());
                    questionBankService.save(bank3);
                    break;

                // 保存到题库
                default:
                    throw new DefaultException("复制失败请联系管理员处理");
            }
        }
        // 2.2复制题库信息 - 考试问卷
        List<ExamQuestionBank> examQuestionBankList = examQuestionBankService.getExamQuestionBankListByQuestionnaireId(Integer.parseInt(id));
        examQuestionBankList.forEach(examQuestionBank -> {
            switch (examQuestionBank.getType()) {
                case 1:
                    ExamFillIn fillIn = examFillInMapper.selectById(examQuestionBank.getQuestionId());
                    ExamFillIn newFillIn = new ExamFillIn();
                    newFillIn.setIsDeleted(false);
                    newFillIn.setQuestion(fillIn.getQuestion());
                    newFillIn.setDesc(fillIn.getDesc());
                    newFillIn.setRequired(fillIn.getRequired());
                    examFillInMapper.insert(newFillIn);
                    break;
                case 2:
                    ExamMulti multi = examMultiMapper.selectById(examQuestionBank.getQuestionId());
                    ExamMulti newMulti = new ExamMulti();
                    newMulti.setQuestion(multi.getQuestion());
                    newMulti.setScore(multi.getScore());
                    newMulti.setRequired(multi.getRequired());
                    newMulti.setIsDeleted(false);
                    newMulti.setAnswer(multi.getAnswer());
                    newMulti.setChoices(multi.getChoices());
                    newMulti.setDesc(multi.getDesc());
                    examMultiMapper.insert(newMulti);
                    break;
                case 3:
                    ExamSingle single = examSingleMapper.selectById(examQuestionBank.getQuestionId());
                    ExamSingle newSingle = new ExamSingle();
                    newSingle.setQuestion(single.getQuestion());
                    newSingle.setScore(single.getScore());
                    newSingle.setRequired(single.getRequired());
                    newSingle.setIsDeleted(false);
                    newSingle.setAnswer(single.getAnswer());
                    newSingle.setChoices(single.getChoices());
                    newSingle.setDesc(single.getDesc());
                    examSingleMapper.insert(single);
                    break;
            }
        });
        if (delete.equals(1)) {
            questionnaire.setDeleted(true);
            baseMapper.updateById(questionnaire);
        }
        return questionNew;
    }

    private Questionnaire getQuestionnaireByCode(String code) {
        QueryWrapper<Questionnaire> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        List<Questionnaire> questionnaires = baseMapper.selectList(wrapper);
        if (questionnaires.size() == 0) {
            throw new DefaultException("该问卷不存在或已失效！");
        }
        return questionnaires.get(0);
    }


    @Override
    public QuestionnaireDetailVo detailQuestion(String code) {
        Questionnaire questionnaire = getQuestionnaireByCode(code);
        // 获取题库集合
        List<QuestionBank> bankList = questionBankService.findByQuestionId(questionnaire.getId());
        // 存放题库
        List<Object> itemList = new ArrayList<>();
        // 遍历集合
        for (QuestionBank bank : bankList) {
            switch (bank.getType()) {
                case 1:
                    // 填空题
                    FillBlankVo fillBlankVo = getFillBlankVoById(bank);
                    itemList.add(fillBlankVo);
                    break;
                case 2:
                    // 评分题
                    MarkQuestionVo markVo = getMarkVoById(bank);
                    itemList.add(markVo);
                    break;
                case 3:
                    // 多选题
                    MultiChoiceQuestionVo multiChoiceVo = getMultiChoiceVoById(bank);
                    itemList.add(multiChoiceVo);
                    break;
                case 4:
                    // 单选题
                    SingleQuestionChoiceVo singleChoiceVo = getSingleChoiceVoById(bank);
                    itemList.add(singleChoiceVo);
                    break;
                case 5:
                    // 报名单选题
                    SingleQuestionChoiceApplyVo singleChoiceApplyVoById = getSingleChoiceApplyVoById(bank);
                    itemList.add(singleChoiceApplyVoById);
                    break;
                case 6:
                    // 报名多选题
                    MultiChoiceApplyQuestionVo multiChoiceApplyVoById = getMultiChoiceApplyVoById(bank);
                    itemList.add(multiChoiceApplyVoById);
                    break;
                default:
                    itemList.add("题库错误，查无此题目");
            }
        }

        QuestionnaireDetailVo questionnaireDetailVo = new QuestionnaireDetailVo();
        questionnaireDetailVo.setId(questionnaire.getId());
        questionnaireDetailVo.setHead(questionnaire.getHead());
        questionnaireDetailVo.setUserId(questionnaire.getUserId());
        questionnaireDetailVo.setNickName(userService.findById(questionnaire.getUserId()).getNickname());
        questionnaireDetailVo.setCreateTime(questionnaire.getCreateTime());
        questionnaireDetailVo.setIntroduction(questionnaire.getIntroduction());
        questionnaireDetailVo.setType(questionnaire.getType());
        questionnaireDetailVo.setDeleted(questionnaire.isDeleted());
        questionnaireDetailVo.setIsReleased(questionnaire.getIsReleased());
        questionnaireDetailVo.setStartTime(questionnaire.getStartTime());
        questionnaireDetailVo.setEndTime(questionnaire.getEndTime());
        questionnaireDetailVo.setWriteNum(questionnaire.getWriteNum());
        questionnaireDetailVo.setSerial(questionnaire.isSerial());
        questionnaireDetailVo.setStamp(questionnaire.getStamp());
        questionnaireDetailVo.setMaxNum(questionnaire.getMaxNum());
        questionnaireDetailVo.setItemList(itemList);


        return questionnaireDetailVo;
    }

    @Override
    @Transactional
    public boolean fillIn(List<FillInQuestionnaireReq> reqs, String code) {
        Questionnaire questionnaire = getQuestionnaireByCode(code);
        int userId = 0;
        if (questionnaire.getType().equals(2)) {
            userId = StpUtil.getLoginIdAsInt();
        }
        AnswerRecord answerRecord = new AnswerRecord();
        answerRecord.setQuestionnaireId(questionnaire.getId());
        answerRecord.setAnswerTime(new Date());
        answerRecord.setAnswerUsrId(userId);
        answerRecordMapper.insert(answerRecord);
        // 校验问答资格
        fillInFlag(questionnaire, code);

        // 记录用户回答的答案
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setQuestionId(questionnaire.getId());
        userAnswer.setAnswer(JSON.toJSONString(reqs));
        userAnswerMapper.insert(userAnswer);

        for (FillInQuestionnaireReq req : reqs) {
            if (req.getAnswerList().size() > 0) {
                switch (req.getItemType()) {
                    case 1:
                        FillBlankAnswer fillBlankAnswer = new FillBlankAnswer();
                        fillBlankAnswer.setQuestionId(req.getTopicId());
                        fillBlankAnswer.setAnswer(req.getAnswerList().get(0));
                        if (questionnaire.getType().equals(2)) {
                            fillBlankAnswer.setAnswerUser(userId);
                        }
                        fillBlankAnswer.setRecordId(answerRecord.getId());
                        fillBlankAnswerMapper.insert(fillBlankAnswer);
                        break;
                    case 2:
                        // 评分题
                        MarkAnswer markAnswer = new MarkAnswer();
                        markAnswer.setQuestionId(req.getTopicId());
                        markAnswer.setAnswer(Integer.parseInt(req.getAnswerList().get(0)));
                        if (questionnaire.getType().equals(2)) {
                            markAnswer.setAnswerUser(userId);
                        }
                        markAnswer.setRecordId(answerRecord.getId());
                        markAnswerMapper.insert(markAnswer);
                        break;
                    case 3:
                        // 多选题
                        MultiChoiceAnswer multiChoiceAnswer = new MultiChoiceAnswer();
                        multiChoiceAnswer.setQuestionId(req.getTopicId());
                        multiChoiceAnswer.setAnswer(JSON.toJSONString(req.getAnswerList()));
                        if (questionnaire.getType().equals(2)) {
                            multiChoiceAnswer.setAnswerUser(userId);
                        }
                        multiChoiceAnswer.setRecordId(answerRecord.getId());
                        multiChoiceAnswerMapper.insert(multiChoiceAnswer);
                        break;
                    case 4:
                        // 单选题
                        SingleChoiceAnswer singleChoiceAnswer = new SingleChoiceAnswer();
                        singleChoiceAnswer.setQuestionId(req.getTopicId());
                        singleChoiceAnswer.setAnswer(req.getAnswerList().get(0));
                        if (questionnaire.getType().equals(2)) {
                            singleChoiceAnswer.setAnswerUser(userId);
                        }
                        singleChoiceAnswer.setRecordId(answerRecord.getId());
                        singleChoiceAnswerMapper.insert(singleChoiceAnswer);
                        break;
                    case 5:
                        // 复杂单选题
                        // 查询该选项是否
                        Integer answer = Integer.parseInt(req.getAnswerList().get(0));
                        ApplyChoiceVo applyVoById = singleChoiceService.findApplyVoById(req.getTopicId());
                        applyVoById.getChoices().forEach(applyOptions -> {
                            if (applyOptions.getId().equals(answer)) {
                                if (applyOptions.getSurplus() > 0) {
                                    applyOptions.setSelected(applyOptions.getSelected() + 1);
                                    applyOptionsMapper.updateById(applyOptions);
                                } else {
                                    throw new DefaultException(applyOptions.getName() + "：此选项已被选择完毕，请重新选择");
                                }
                            }
                        });

                        // 保存单选题信息
                        SingleChoiceAnswer singleChoiceAnswer1 = new SingleChoiceAnswer();
                        singleChoiceAnswer1.setQuestionId(req.getTopicId());
                        singleChoiceAnswer1.setAnswer(req.getAnswerList().get(0));
                        if (questionnaire.getType().equals(2)) {
                            singleChoiceAnswer1.setAnswerUser(userId);
                        }
                        singleChoiceAnswer1.setRecordId(answerRecord.getId());
                        singleChoiceAnswerMapper.insert(singleChoiceAnswer1);
                        break;
                    case 6:
                        // 复杂多选题
                        List<String> answerList = req.getAnswerList();
                        ApplyChoiceVo applyVoById1 = multiChoiceService.findApplyVoById(req.getTopicId());
                        applyVoById1.getChoices().forEach(applyOptions -> {
                            answerList.forEach(s -> {
                                Integer answer2 = Integer.parseInt(s);
                                if (applyOptions.getId().equals(answer2)) {
                                    if (applyOptions.getSurplus() > 0) {
                                        applyOptions.setSelected(applyOptions.getSelected() + 1);
                                        applyOptionsMapper.updateById(applyOptions);
                                    } else {
                                        throw new DefaultException(applyOptions.getName() + "：此选项已被选择完毕，请重新选择");
                                    }
                                }
                            });
                        });

                        MultiChoiceAnswer multiChoiceAnswer2 = new MultiChoiceAnswer();
                        multiChoiceAnswer2.setQuestionId(req.getTopicId());
                        multiChoiceAnswer2.setAnswer(JSON.toJSONString(req.getAnswerList()));
                        if (questionnaire.getType().equals(2)) {
                            multiChoiceAnswer2.setAnswerUser(userId);
                        }
                        multiChoiceAnswer2.setRecordId(answerRecord.getId());
                        multiChoiceAnswerMapper.insert(multiChoiceAnswer2);
                        break;
                    default:
                        throw new DefaultException("数据有误");
                }
            }

        }

        if (questionnaire.getType().equals(2)) {
            // 保存到问卷用户填写表中
            QuestionnaireUser questionnaireUser = new QuestionnaireUser();
            questionnaireUser.setQuestionnaireId(questionnaire.getId());
            questionnaireUser.setUserId(userId);
            questionnaireUserMapper.insert(questionnaireUser);
        }
        // 问卷回答数+1
        questionnaire.setWriteNum(questionnaire.getWriteNum() + 1);
        baseMapper.updateById(questionnaire);
        return true;
    }

    /**
     * 校验问卷作答资格
     *
     * @param questionnaire 问卷信息
     */
    private void fillInFlag(Questionnaire questionnaire, String code) {
        if (questionnaire == null || questionnaire.isDeleted()) {
            throw new DefaultException("找不到该问卷，可能已被删除");
        }
        if (questionnaire.getIsReleased().equals(0)) {
            throw new DefaultException("该问卷未开始收集");
        }
        if (questionnaire.getStartTime().getTime() > System.currentTimeMillis()) {
            throw new DefaultException("未到问卷收集时间");
        }
        if (questionnaire.getEndTime().getTime() < System.currentTimeMillis()) {
            throw new DefaultException("问卷已停止收集");
        }
        if (questionnaire.getMaxNum() - questionnaire.getWriteNum() <= 0) {
            throw new DefaultException("该问卷收集完毕，已达最大填写数量");
        }
        if (questionnaire.getType().equals(2) || questionnaire.getType().equals(3)) {
            int userId = StpUtil.getLoginIdAsInt();
            // 如果是需要登录，才能填写的问卷，则查看用户是否已填写
            QueryWrapper<QuestionnaireUser> wrapper = new QueryWrapper<>();
            wrapper.eq("questionnaire_id", questionnaire.getId());
            wrapper.eq("user_id", userId);
            Integer count = questionnaireUserMapper.selectCount(wrapper);
            if (count > 0) {
                throw new DefaultException("您已填写过此问卷");
            } else {
                QuestionnaireUser questionnaireUser = new QuestionnaireUser();
                questionnaireUser.setQuestionnaireId(questionnaire.getId());
                questionnaireUser.setUserId(userId);
                questionnaireUserMapper.insert(questionnaireUser);
            }
        }
        // 邀请码不做判断
//        if (questionnaire.getType().equals(1) || questionnaire.getType().equals(3)) {
//            if (!questionnaire.getCode().equals(code)) {
//                throw new DefaultException("请输入正确的邀请码");
//            }
//        }
    }

    @Override
    public Questionnaire createQuestionnaireCode(CreateQuestionnaireReq req) {
        int userId = StpUtil.getLoginIdAsInt();
        // 填入问卷信息
        Questionnaire questionnaire = saveQuestionnaireCode(req, userId);
        // 拆解问卷问题，保存问卷信息到题库表
        if (req.getStamp() != 5) {
            saveQuestionBank(req.getItemList(), questionnaire);
        } else {
            saveExamQuestionBank(req.getItemList(), questionnaire.getId());
        }
        return questionnaire;
    }

    @Override
    public List<AnswerVo> getAnswer(String id) {
        Questionnaire questionnaire = baseMapper.selectById(id);
        if (questionnaire == null){
            throw new DefaultException("未查询到问卷相关信息");
        }
        if (!questionnaire.getStamp().equals(2)) {
            int userId = StpUtil.getLoginIdAsInt();
            if (!questionnaire.getUserId().equals(userId)) {
                throw new DefaultException("只有问卷的发起者才能查看问卷信息");
            }
        }

        List<QuestionBank> banks = questionBankService.findByQuestionId(questionnaire.getId());
        banks.sort(new Comparator<QuestionBank>() {
            @Override
            public int compare(QuestionBank o1, QuestionBank o2) {
                return o2.getSequence() - o1.getSequence();
            }
        });
        // 查询答案集合
        List<AnswerVo> voList = new ArrayList<>();

        for (QuestionBank bank : banks) {
            switch (bank.getType()) {
                case 1:
                    // 1.获取填空题题目
                    FillBlank fillBlank = fillBlankService.findById(bank.getTopicId());
                    // 2.获取该题的所有答案集合
                    QueryWrapper<FillBlankAnswer> wrapper = new QueryWrapper<>();
                    wrapper.eq("question_id", fillBlank.getId());
                    wrapper.select("answer");
                    List<FillBlankAnswer> fillBlankAnswerList = fillBlankAnswerMapper.selectList(wrapper);

                    // 答案+数量集合
                    List<AnswerItem<String>> answerItemList = new ArrayList<>();
                    // 遍历答案
                    for (FillBlankAnswer fillBlankAnswer : fillBlankAnswerList) {
                        // 获取答案
                        String answer = fillBlankAnswer.getAnswer();
                        // 如果答案+数量集合<0代表没有数据，直接添加
                        if (answerItemList.size() > 0) {
                            boolean flag = true;
                            // 遍历答案+数量集合，如果答案相同则数量+1
                            for (int i = 0; i < answerItemList.size(); i++) {
                                AnswerItem<String> answerItem = answerItemList.get(i);
                                if (answerItem.getItem().equals(answer)) {
                                    answerItem.setNum(answerItem.getNum() + 1);
                                    answerItemList.set(i, answerItem);
                                    flag = false;
                                    break;
                                }
                            }
                            // 如果答案不同则新建
                            if (flag) {
                                AnswerItem<String> answerItem = new AnswerItem<String>();
                                answerItem.setItem(answer);
                                answerItem.setNum(1);
                                answerItemList.add(answerItem);
                            }
                        } else {
                            AnswerItem<String> answerItem = new AnswerItem<String>();
                            answerItem.setItem(answer);
                            answerItem.setNum(1);
                            answerItemList.add(answerItem);
                        }
                    }
                    AnswerVo<String> answerVo = new AnswerVo();
                    answerVo.setQuestion(fillBlank.getQuestion());
                    answerVo.setChoices(answerItemList);

                    voList.add(answerVo);
                    break;
                case 2:
                    // 1.获取评分题题目
                    Mark mark = markService.findById(bank.getTopicId());
                    // 2.获取该题的所有答案集合
                    QueryWrapper<MarkAnswer> wrapper2 = new QueryWrapper<>();
                    wrapper2.eq("question_id", mark.getId());
                    wrapper2.select("answer");
                    List<MarkAnswer> markAnswerList = markAnswerMapper.selectList(wrapper2);

                    // 答案+数量集合
                    List<AnswerItem<String>> answerItemList2 = new ArrayList<>();

                    // 遍历答案
                    for (MarkAnswer markAnswer : markAnswerList) {
                        // 获取答案
                        Integer answer = markAnswer.getAnswer();
                        if (answerItemList2.size() > 0) {
                            boolean flag = true;
                            // 遍历答案+数量集合，如果答案相同则数量+1
                            for (int i = 0; i < answerItemList2.size(); i++) {
                                AnswerItem<String> answerItem = answerItemList2.get(i);
                                if (answer.equals(Integer.parseInt(answerItem.getItem()))) {
                                    answerItem.setNum(answerItem.getNum() + 1);
                                    answerItemList2.set(i, answerItem);
                                    flag = false;
                                    break;
                                }
                            }
                            // 如果答案不同则新建
                            if (flag) {
                                AnswerItem<String> answerItem = new AnswerItem<>();
                                answerItem.setItem(answer + "");
                                answerItem.setNum(1);
                                answerItemList2.add(answerItem);
                            }
                        } else {
                            AnswerItem<String> answerItem = new AnswerItem<>();
                            answerItem.setItem(answer + "");
                            answerItem.setNum(1);
                            answerItemList2.add(answerItem);
                        }
                    }

                    AnswerVo<String> answerVo2 = new AnswerVo<>();
                    answerVo2.setQuestion(mark.getQuestion());
                    answerVo2.setChoices(answerItemList2);

                    voList.add(answerVo2);
                    break;
                case 3:
                    // 1.获取多选题题目
                    MultiChoiceVo multiChoice = multiChoiceService.findVoById(bank.getTopicId());
                    // 2.获取该题的所有答案集合
                    QueryWrapper<MultiChoiceAnswer> wrapper3 = new QueryWrapper<>();
                    wrapper3.eq("question_id", multiChoice.getId());
                    wrapper3.select("answer");
                    List<MultiChoiceAnswer> multiChoiceAnswerList = multiChoiceAnswerMapper.selectList(wrapper3);

                    // 答案+数量集
                    List<AnswerItem<String>> answerItemList3 = new ArrayList<>();
                    // 获取答案
                    List<String> answerList = multiChoice.getChoices();
                    answerList.forEach(s -> {
                        AnswerItem<String> answerItem = new AnswerItem<>();
                        answerItem.setItem(s);
                        answerItem.setNum(0);
                        answerItemList3.add(answerItem);
                    });


                    multiChoiceAnswerList.forEach(multiChoiceAnswer -> {
                        List<String> answerListString = JSON.parseObject(multiChoiceAnswer.getAnswer(), List.class);
                        // 遍历当前用户输入的答案
                        for (String s : answerListString) {
                            // 如果答案登录集合重点其中一个，对应答案+1
                            for (int i = 0; i < answerItemList3.size(); i++) {
                                AnswerItem<String> answerItem = answerItemList3.get(i);
                                if (answerItem.getItem().equals(s)) {
                                    answerItem.setNum(answerItem.getNum() + 1);
                                    answerItemList3.set(i, answerItem);
                                }
                            }
                        }
                    });


                    AnswerVo<String> answerVo3 = new AnswerVo<>();
                    answerVo3.setQuestion(multiChoice.getQuestion());
                    answerVo3.setChoices(answerItemList3);

                    voList.add(answerVo3);
                    break;
                case 4:
                    // 1.获取单选题题目
                    SingleChoiceVo singleChoice = singleChoiceService.findVoById(bank.getTopicId());
                    // 2.获取该题的所有答案集合
                    QueryWrapper<SingleChoiceAnswer> wrapper4 = new QueryWrapper<>();
                    wrapper4.eq("question_id", singleChoice.getId());
                    wrapper4.select("answer");
                    List<SingleChoiceAnswer> singleChoiceAnswerList = singleChoiceAnswerMapper.selectList(wrapper4);

                    // 答案+数量集合
                    List<AnswerItem<String>> answerItemList4 = new ArrayList<>();

                    // 获取答案
                    List<String> choices = singleChoice.getChoices();
                    choices.forEach(s -> {
                        AnswerItem<String> answerItem = new AnswerItem<>();
                        answerItem.setItem(s);
                        answerItem.setNum(0);
                        answerItemList4.add(answerItem);
                    });

                    // 遍历答案
                    for (SingleChoiceAnswer singleChoiceAnswer : singleChoiceAnswerList) {
                        // 获取答案
                        String answer = singleChoiceAnswer.getAnswer();
                        for (int i = 0; i < answerItemList4.size(); i++) {
                            AnswerItem<String> answerItem = answerItemList4.get(i);
                            if (answer.equals(answerItem.getItem())) {
                                answerItem.setNum(answerItem.getNum() + 1);
                                answerItemList4.set(i, answerItem);
                            }
                        }
                    }

                    AnswerVo<String> answerVo4 = new AnswerVo<>();
                    answerVo4.setQuestion(singleChoice.getQuestion());
                    answerVo4.setChoices(answerItemList4);

                    voList.add(answerVo4);
                    break;
                case 5:
                    // 复杂单选题
                    // 1.获取单选题题目
                    ApplyChoiceVo applyVoById = singleChoiceService.findApplyVoById(bank.getTopicId());
                    // 2.获取该题的所有答案集合
                    QueryWrapper<SingleChoiceAnswer> wrapper5 = new QueryWrapper<>();
                    wrapper5.eq("question_id", applyVoById.getId());
                    wrapper5.select("answer");
                    List<SingleChoiceAnswer> singleChoiceAnswers = singleChoiceAnswerMapper.selectList(wrapper5);

                    // 答案+数量集合
                    List<AnswerItem<Integer>> answerItemList5 = new ArrayList<>();

                    // 获取答案
                    applyVoById.getChoices().forEach(applyOptions -> {
                        AnswerItem<Integer> answerItem = new AnswerItem<>();
                        answerItem.setItem(applyOptions.getId());
                        answerItem.setNum(0);
                        answerItemList5.add(answerItem);
                    });

                    // 遍历答案
                    for (SingleChoiceAnswer singleChoiceAnswer : singleChoiceAnswers) {
                        // 获取答案
                        Integer answer = Integer.parseInt(singleChoiceAnswer.getAnswer());
                        for (int i = 0; i < answerItemList5.size(); i++) {
                            AnswerItem<Integer> answerItem = answerItemList5.get(i);
                            if (answer.equals(answerItem.getItem())) {
                                answerItem.setNum(answerItem.getNum() + 1);
                                answerItemList5.set(i, answerItem);
                            }
                        }
                    }

                    List<AnswerItem<String>> answerItems5 = new ArrayList<>();
                    answerItemList5.forEach(integerAnswerItem -> {
                        applyVoById.getChoices().forEach(applyOptions -> {
                            if (applyOptions.getId().equals(integerAnswerItem.getItem())) {
                                AnswerItem<String> stringAnswerItem = new AnswerItem<>();
                                stringAnswerItem.setItem(applyOptions.getName());
                                stringAnswerItem.setNum(integerAnswerItem.getNum());
                                answerItems5.add(stringAnswerItem);
                            }
                        });
                    });

                    AnswerVo<String> answerVo5 = new AnswerVo<>();
                    answerVo5.setQuestion(applyVoById.getQuestion());
                    answerVo5.setChoices(answerItems5);

                    voList.add(answerVo5);
                    break;
                case 6:
                    // 复杂多选题
                    // 1.获取多选题题目
                    ApplyChoiceVo applyVo = multiChoiceService.findApplyVoById(bank.getTopicId());
                    // 2.获取该题的所有答案集合
                    QueryWrapper<MultiChoiceAnswer> wrapper6 = new QueryWrapper<>();
                    wrapper6.eq("question_id", applyVo.getId());
                    wrapper6.select("answer");
                    List<MultiChoiceAnswer> multiChoiceAnswers = multiChoiceAnswerMapper.selectList(wrapper6);

                    // 答案+数量集
                    List<AnswerItem<Integer>> answerItemList6 = new ArrayList<>();
                    // 获取答案
                    applyVo.getChoices().forEach(s -> {
                        AnswerItem<Integer> answerItem = new AnswerItem<>();
                        answerItem.setItem(s.getId());
                        answerItem.setNum(0);
                        answerItemList6.add(answerItem);
                    });


                    multiChoiceAnswers.forEach(multiChoiceAnswer -> {
                        List<String> answerListInteger = JSON.parseObject(multiChoiceAnswer.getAnswer(), List.class);
                        // 遍历当前用户输入的答案
                        for (String answerString : answerListInteger) {
                            Integer answerInt = Integer.parseInt(answerString);
                            // 如果答案登录集合重点其中一个，对应答案+1
                            for (int i = 0; i < answerItemList6.size(); i++) {
                                AnswerItem<Integer> answerItem = answerItemList6.get(i);
                                if (answerItem.getItem().equals(answerInt)) {
                                    answerItem.setNum(answerItem.getNum() + 1);
                                    answerItemList6.set(i, answerItem);
                                }
                            }
                        }
                    });

                    List<AnswerItem<String>> answerItems6 = new ArrayList<>();
                    answerItemList6.forEach(integerAnswerItem -> {
                        applyVo.getChoices().forEach(applyOptions -> {
                            if (applyOptions.getId().equals(integerAnswerItem.getItem())) {
                                AnswerItem<String> stringAnswerItem = new AnswerItem<>();
                                stringAnswerItem.setItem(applyOptions.getName());
                                stringAnswerItem.setNum(integerAnswerItem.getNum());
                                answerItems6.add(stringAnswerItem);
                            }
                        });
                    });

                    AnswerVo<String> answerVo6 = new AnswerVo<>();
                    answerVo6.setQuestion(applyVo.getQuestion());
                    answerVo6.setChoices(answerItems6);

                    voList.add(answerVo6);
                    break;
                default:
                    throw new DefaultException("问卷出错，无法正常访问，请联系管理员处理");
            }
        }
        return voList;
    }

    @Override
    public Questionnaire updateQuestionnaire(UpdateQuestionnaireReq req) {
        int userId = StpUtil.getLoginIdAsInt();
        Questionnaire question = baseMapper.selectById(req.getId());
        if (!question.getUserId().equals(userId)) {
            throw new DefaultException("您没有修改问卷的权限");
        }

        // 修改信息
        question.setHead(req.getHead());
        question.setIntroduction(req.getIntroduction());
        question.setIsReleased(req.getIsReleased());
        question.setStartTime(req.getStartTime());
        question.setEndTime(req.getEndTime());
        question.setStamp(req.getStamp());
        question.setMaxNum(req.getMaxNum());
        question.setSerial(req.isSerial());

        baseMapper.updateById(question);

        // 清空之前的题库信息
        questionBankService.deleteByQuestionnaireId(question.getId());

        // 拆解问卷问题，保存问卷信息到题库表
        if (req.getStamp() != 5) {
            saveQuestionBank(req.getItemList(), question);
        } else {
            saveExamQuestionBank(req.getItemList(), question.getId());
        }

        //保存问卷
        baseMapper.updateById(question);

        //更新定时任务
        scheduleService.updateTriggerTask(question.getId());

        return question;
    }

    @Override
    public boolean fillInIsFlag(String id, String code) {
        Questionnaire questionnaire = baseMapper.selectById(id);
        // 校验问答资格
        fillInFlag(questionnaire, code);
        return true;
    }


    @Override
    public Questionnaire updateCode(String id) {
        Questionnaire questionnaire = baseMapper.selectById(id);
        Integer userId = StpUtil.getLoginIdAsInt();
        if (!questionnaire.getUserId().equals(userId)) {
            throw new DefaultException("您没有修改邀请码的权限");
        }
        questionnaire.setCode(RandomUtil.randomString(6));
        QueryWrapper<Questionnaire> wrapper = new QueryWrapper<>();
        wrapper.eq("code", questionnaire.getCode());
        while (baseMapper.selectList(wrapper).size() != 0) {
            questionnaire.setCode(RandomUtil.randomString(6));
            wrapper = new QueryWrapper<>();
            wrapper.eq("code", questionnaire.getCode());
        }
        baseMapper.updateById(questionnaire);
        return questionnaire;
    }

    @Override
    public Questionnaire updateType(String id, Integer type) {
        if (!(type.equals(0) || type.equals(1) || type.equals(2) || type.equals(3))) {
            throw new DefaultException("您输入的权限信息不合法");
        }
        Questionnaire questionnaire = baseMapper.selectById(id);
        Integer userId = StpUtil.getLoginIdAsInt();
        if (!questionnaire.getUserId().equals(userId)) {
            throw new DefaultException("您没有修改邀请码的权限");
        }
        questionnaire.setType(type);
        baseMapper.updateById(questionnaire);
        return questionnaire;
    }

    @Override
    public QuestionnaireSimpleVo detailApplyQuestion(String code) {
        Questionnaire questionnaire = getQuestionnaireByCode(code);
        // 获取题库集合
        List<QuestionBank> bankList = questionBankService.findByQuestionId(questionnaire.getId());
        QuestionnaireSimpleVo vo = new QuestionnaireSimpleVo();
        vo.setId(questionnaire.getId());
        vo.setHead(questionnaire.getHead());
        vo.setUserId(questionnaire.getUserId());
        vo.setNickName(userService.findById(questionnaire.getUserId()).getNickname());
        vo.setCreateTime(questionnaire.getCreateTime());
        vo.setIntroduction(questionnaire.getIntroduction());
        vo.setType(questionnaire.getType());
        vo.setDeleted(questionnaire.isDeleted());
        vo.setIsReleased(questionnaire.getIsReleased());
        vo.setStartTime(questionnaire.getStartTime());
        vo.setEndTime(questionnaire.getEndTime());
        vo.setWriteNum(questionnaire.getWriteNum());
        vo.setMaxNum(questionnaire.getMaxNum());
        vo.setSerial(questionnaire.isSerial());

        vo.setStamp(questionnaire.getStamp());

        List<BankVo> itemList = new ArrayList<>();
        bankList.forEach(bank -> {
            BankVo bankVo = new BankVo();
            bankVo.setType(bank.getType());
            bankVo.setTopicId(bank.getTopicId());
            bankVo.setSequence(bank.getSequence());
            itemList.add(bankVo);
        });
        vo.setItemList(itemList);
        return vo;
    }

    /**
     * 查询报名多选题信息
     *
     * @param bank 单个题库对象
     * @return 封装后的多选题vo对象
     */
    private MultiChoiceApplyQuestionVo getMultiChoiceApplyVoById(QuestionBank bank) {
        // 查询多选题信息
        ApplyChoiceVo applyVoById = multiChoiceService.findApplyVoById(bank.getTopicId());
        // 封装多选题
        MultiChoiceApplyQuestionVo multiChoiceQuestionVo = new MultiChoiceApplyQuestionVo();
        multiChoiceQuestionVo.setItemType(6);
        multiChoiceQuestionVo.setId(applyVoById.getId());
        multiChoiceQuestionVo.setQuestion(applyVoById.getQuestion());
        multiChoiceQuestionVo.setChoices(applyVoById.getChoices());
        multiChoiceQuestionVo.setRequired(applyVoById.getRequired());
        multiChoiceQuestionVo.setSequence(bank.getSequence());
        multiChoiceQuestionVo.setDesc(applyVoById.getDesc());
        return multiChoiceQuestionVo;
    }

    /**
     * 查询报名单选题信息
     *
     * @param bank 单个题库对象
     * @return 封装后的单选题vo对象
     */
    private SingleQuestionChoiceApplyVo getSingleChoiceApplyVoById(QuestionBank bank) {
        // 查询单选题信息
        ApplyChoiceVo applyVoById = singleChoiceService.findApplyVoById(bank.getTopicId());
        // 封装单选题
        SingleQuestionChoiceApplyVo singleQuestionChoiceVo = new SingleQuestionChoiceApplyVo();
        singleQuestionChoiceVo.setItemType(5);
        singleQuestionChoiceVo.setId(applyVoById.getId());
        singleQuestionChoiceVo.setQuestion(applyVoById.getQuestion());
        singleQuestionChoiceVo.setChoices(applyVoById.getChoices());
        singleQuestionChoiceVo.setRequired(applyVoById.getRequired());
        singleQuestionChoiceVo.setSequence(bank.getSequence());
        singleQuestionChoiceVo.setDesc(applyVoById.getDesc());
        return singleQuestionChoiceVo;
    }

    /**
     * 查询单选题信息
     *
     * @param bank 单个题库对象
     * @return 封装后的单选题vo对象
     */
    private SingleQuestionChoiceVo getSingleChoiceVoById(QuestionBank bank) {
        // 查询单选题信息
        SingleChoiceVo singleChoiceVo = singleChoiceService.findVoById(bank.getTopicId());
        // 封装单选题
        SingleQuestionChoiceVo singleQuestionChoiceVo = new SingleQuestionChoiceVo();
        singleQuestionChoiceVo.setItemType(4);
        singleQuestionChoiceVo.setId(singleChoiceVo.getId());
        singleQuestionChoiceVo.setQuestion(singleChoiceVo.getQuestion());
        singleQuestionChoiceVo.setChoices(singleChoiceVo.getChoices());
        singleQuestionChoiceVo.setRequired(singleChoiceVo.getRequired());
        singleQuestionChoiceVo.setSequence(bank.getSequence());
        singleQuestionChoiceVo.setDesc(singleChoiceVo.getDesc());
        return singleQuestionChoiceVo;
    }

    /**
     * 查询多选题信息
     *
     * @param bank 单个题库对象
     * @return 封装后的多选题vo对象
     */
    private MultiChoiceQuestionVo getMultiChoiceVoById(QuestionBank bank) {
        // 查询多选题信息
        MultiChoiceVo multiChoiceVo = multiChoiceService.findVoById(bank.getTopicId());
        // 封装多选题
        MultiChoiceQuestionVo multiChoiceQuestionVo = new MultiChoiceQuestionVo();
        multiChoiceQuestionVo.setItemType(3);
        multiChoiceQuestionVo.setId(multiChoiceVo.getId());
        multiChoiceQuestionVo.setQuestion(multiChoiceVo.getQuestion());
        multiChoiceQuestionVo.setChoices(multiChoiceVo.getChoices());
        multiChoiceQuestionVo.setRequired(multiChoiceVo.getRequired());
        multiChoiceQuestionVo.setSequence(bank.getSequence());
        multiChoiceQuestionVo.setDesc(multiChoiceVo.getDesc());
        return multiChoiceQuestionVo;
    }

    /**
     * 查询评分题信息
     *
     * @param bank 单个题库对象
     * @return 封装后的评分题vo对象
     */
    private MarkQuestionVo getMarkVoById(QuestionBank bank) {
        // 查询评分题信息
        Mark mark = markService.findById(bank.getTopicId());
        // 封装评分题
        MarkQuestionVo markQuestionVo = new MarkQuestionVo();
        markQuestionVo.setItemType(2);
        markQuestionVo.setId(mark.getId());
        markQuestionVo.setQuestion(mark.getQuestion());
        markQuestionVo.setMaxScore(mark.getMaxScore());
        markQuestionVo.setRequired(mark.getRequired());
        markQuestionVo.setSequence(bank.getSequence());
        markQuestionVo.setDesc(mark.getDesc());
        return markQuestionVo;
    }


    /**
     * 查询填空题信息
     *
     * @param bank 单个题库对象
     * @return 封装后的填空题vo对象
     */
    private FillBlankVo getFillBlankVoById(QuestionBank bank) {
        // 查询填空题信息
        FillBlank fillBlank = fillBlankService.findById(bank.getTopicId());
        // 封装填空题
        FillBlankVo fillBlankVo = new FillBlankVo();
        fillBlankVo.setItemType(1);
        fillBlankVo.setTopicId(bank.getTopicId());
        fillBlankVo.setQuestion(fillBlank.getQuestion());
        fillBlankVo.setRequired(fillBlank.isRequired());
        fillBlankVo.setSequence(bank.getSequence());
        fillBlankVo.setDesc(fillBlank.getDesc());
        return fillBlankVo;
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
     *
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
            } else if (req.getSortBy().equals(3)) {
                wrapper.orderByAsc("create_time");
            } else if (req.getSortBy().equals(4)) {
                wrapper.orderByAsc("write_num");
            }
        }
        wrapper.eq("user_id", userId);
        return wrapper;
    }

    /**
     * 保存问卷信息
     *
     * @param req    问卷信息
     * @param userId 用户id
     * @param type   问卷权限
     */
    private Questionnaire saveQuestionnaire(CreateQuestionnaireReq req, int userId, Integer type) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setHead(req.getHead());
        questionnaire.setUserId(userId);
        questionnaire.setCreateTime(new Date());
        questionnaire.setIntroduction(req.getIntroduction());
        questionnaire.setType(type);
        questionnaire.setDeleted(false);
        questionnaire.setIsReleased(req.getIsReleased());
        questionnaire.setStartTime(req.getStartTime());
        questionnaire.setEndTime(req.getEndTime());
        questionnaire.setWriteNum(0);
        questionnaire.setCode(RandomUtil.randomString(6));
        QueryWrapper<Questionnaire> wrapper = new QueryWrapper<>();
        wrapper.eq("code", questionnaire.getCode());
        List<Questionnaire> questionnaires = baseMapper.selectList(wrapper);
        while (questionnaires.size() != 0) {
            questionnaire.setCode(RandomUtil.randomString(6));
            wrapper = new QueryWrapper<>();
            wrapper.eq("code", questionnaire.getCode());
            questionnaires = baseMapper.selectList(wrapper);
        }
        questionnaire.setSerial(req.isSerial());
        questionnaire.setStamp(req.getStamp());
        questionnaire.setMaxNum(req.getMaxNum());
        baseMapper.insert(questionnaire);
        return questionnaire;
    }

    /**
     * 保存问卷信息
     *
     * @param req    问卷信息
     * @param userId 用户id
     */
    private Questionnaire saveQuestionnaireCode(CreateQuestionnaireReq req, int userId) {
        if (req.getStartTime().getTime() >= req.getEndTime().getTime()) {
            throw new DefaultException("开始时间必须小于结束时间");
        }
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setHead(req.getHead());
        questionnaire.setUserId(userId);
        questionnaire.setCreateTime(new Date());
        questionnaire.setIntroduction(req.getIntroduction());
        // 邀请码填写
        questionnaire.setType(1);
        questionnaire.setDeleted(false);
        questionnaire.setIsReleased(req.getIsReleased());
        questionnaire.setStartTime(req.getStartTime());
        questionnaire.setEndTime(req.getEndTime());
        questionnaire.setWriteNum(0);
        questionnaire.setStamp(req.getStamp());
        questionnaire.setSerial(req.isSerial());
        // 邀请码
        questionnaire.setCode(RandomUtil.randomString(6));
        baseMapper.insert(questionnaire);
        return questionnaire;
    }

}
