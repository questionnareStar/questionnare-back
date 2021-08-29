package com.question.modules.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.question.modules.question.Util.DateUtil;
import com.question.modules.question.entities.Questionnaire;
import com.question.modules.question.mapper.QuestionnaireMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import com.question.modules.question.Util.BeanUtil;

@Service
@EnableScheduling
public class ScheduleService implements SchedulingConfigurer {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    private ScheduledTaskRegistrar scheduledTaskRegistrar;
    private final String FIELD_SCHEDULED_FUTURES = "scheduledFutures";
    private ScheduledTaskRegistrar taskRegistrar;
    private Set<ScheduledFuture<?>> scheduledFutures = null;
    private Map<Integer, ScheduledFuture<?>> taskFutures = new ConcurrentHashMap<Integer, ScheduledFuture<?>>();
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        this.scheduledTaskRegistrar = scheduledTaskRegistrar;
        QueryWrapper<Questionnaire> wrapper = new QueryWrapper<>();
        wrapper.ge("end_time",new Date());
        List<Questionnaire> questionnaires = questionnaireMapper.selectList(wrapper);
        for(Questionnaire questionnaire:questionnaires){
            Date endTime = questionnaire.getEndTime();
            String cron = DateUtil.getCron(endTime);
            QuestionnaireTask task = new QuestionnaireTask(questionnaire.getId());
            scheduledTaskRegistrar.addCronTask(task,cron);
        }
    }
    @SuppressWarnings("unchecked")
    private Set<ScheduledFuture<?>> getScheduledFutures()
    {
        if (scheduledFutures == null)
        {
            try
            {
                scheduledFutures = (Set<ScheduledFuture<?>>) BeanUtil.getProperty(taskRegistrar, FIELD_SCHEDULED_FUTURES);
            }
            catch (NoSuchFieldException e)
            {
                throw new SchedulingException("not found scheduledFutures field.");
            }
        }
        return scheduledFutures;
    }

    public void addQuestionnaireTask(Integer questionnaireId){
        if (taskFutures.containsKey(questionnaireId))
        {
            System.out.println("the taskId[" + questionnaireId + "] exists ,no change happened.");
        }
        Questionnaire questionnaire = questionnaireMapper.selectById(questionnaireId);
        TaskScheduler scheduler = scheduledTaskRegistrar.getScheduler();
        ScheduledFuture<?> future = scheduler.schedule(new QuestionnaireTask(questionnaireId),questionnaire.getEndTime());
        taskFutures.put(questionnaireId,future);
    }

    public void cancelQuestionnaireTask(Integer QuestionnaireId)
    {
        ScheduledFuture<?> future = taskFutures.get(QuestionnaireId);
        if (future != null)
        {
            future.cancel(true);
        }
        taskFutures.remove(QuestionnaireId);
        getScheduledFutures().remove(future);
    }

    //更新
    public void updateTriggerTask(Integer questionnaireId)
    {
        cancelQuestionnaireTask(questionnaireId);
        addQuestionnaireTask(questionnaireId);
    }

    public Set<Integer> taskIds()
    {
        return taskFutures.keySet();
    }
    /**
     * 任务调度是否已经初始化完成
     *
     * @return
     */
    public boolean inited()
    {
        return this.taskRegistrar != null && this.taskRegistrar.getScheduler() != null;
    }

    private class QuestionnaireTask implements Runnable{
        Integer questionnaireId;
        @Override
        public void run() {
            System.out.println("running,questionnaireId = "+questionnaireId);
            Questionnaire questionnaire = questionnaireMapper.selectById(questionnaireId);
            if(questionnaire.getEndTime().before(new Date())||questionnaire.getEndTime().equals(new Date())){
                questionnaire.setIsReleased(0);
                questionnaireMapper.updateById(questionnaire);
            }
        }
        public QuestionnaireTask(Integer questionnaireId){
            this.questionnaireId = questionnaireId;
        }
    }

}
