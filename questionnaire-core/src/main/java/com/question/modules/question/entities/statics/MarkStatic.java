package com.question.modules.question.entities.statics;

import java.util.ArrayList;
import java.util.List;

public class MarkStatic {
    String topic;
    List<ScoreCount> scoreCounts = new ArrayList<>();
    Integer maxScore;

    public MarkStatic(String topic, Integer maxScore) {
        this.topic = topic;
        this.maxScore = maxScore;
    }

    public void addScoreCount(Integer score, Integer number) {
        ScoreCount scoreCount = new ScoreCount(score, number);
        this.scoreCounts.add(scoreCount);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"type\":2,\"topic\":\"").append(topic).append("\",\"maxScore\":").append(maxScore).append(",\"choices\":[");
        for (ScoreCount scoreCount : scoreCounts) {
            if (scoreCount.equals(scoreCounts.get(0))) {
                stringBuilder.append(scoreCount);
            } else {
                stringBuilder.append(",").append(scoreCount);
            }
        }
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }

    static class ScoreCount {
        Integer mark;
        Integer number;

        public ScoreCount(Integer mark, Integer number) {
            this.mark = mark;
            this.number = number;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"mark\":\"" + mark + '\"' +
                    ", \"number\":" + number +
                    '}';
        }
    }
}
