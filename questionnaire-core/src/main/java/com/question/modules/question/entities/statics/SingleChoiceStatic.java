package com.question.modules.question.entities.statics;

import java.util.ArrayList;
import java.util.List;

public class SingleChoiceStatic {
    String topic;
    List<Choice> choices = new ArrayList<>();

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"type\":4,\"topic\":\"").append(topic).append("\",\"choices\":[");
        for (Choice choice : choices) {
            if (choice.equals(choices.get(0))) {
                stringBuilder.append(choice);
            } else {
                stringBuilder.append(",").append(choice);
            }
        }
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }

    public SingleChoiceStatic(String topic){
        this.topic = topic;
    }

    public void append(String option,Integer number) {
        Choice choice = new Choice(option,number);
        choices.add(choice);
    }

    static class Choice {
        String choice;
        Integer number;

        public Choice(String choice, Integer number) {
            this.choice = choice;
            this.number = number;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"choice\":\"" + choice + '\"' +
                    ", \"number\":" + number +
                    '}';
        }
    }
}
