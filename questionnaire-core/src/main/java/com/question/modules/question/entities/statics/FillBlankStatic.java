package com.question.modules.question.entities.statics;

import java.util.ArrayList;
import java.util.List;

public class FillBlankStatic {
    String topic;
    List<String> answers = new ArrayList<>();

    public FillBlankStatic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void addAnswer(String answer) {
        answers.add(answer);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"type\":1,\"topic\":\"").append(topic).append("\",\"answers\":[");
        for (String answer : answers) {
            stringBuilder.append("\"").append(answer).append("\"").append(",");
        }
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }
}
