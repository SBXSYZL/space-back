package com.project.demo.DO;

public class ElectiveDO extends ElectiveDOKey {
    private Float examScore;

    private Float performanceScore;

    private Float workScore;

    private Integer completed;

    public Float getExamScore() {
        return examScore;
    }

    public void setExamScore(Float examScore) {
        this.examScore = examScore;
    }

    public Float getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(Float performanceScore) {
        this.performanceScore = performanceScore;
    }

    public Float getWorkScore() {
        return workScore;
    }

    public void setWorkScore(Float workScore) {
        this.workScore = workScore;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }
}