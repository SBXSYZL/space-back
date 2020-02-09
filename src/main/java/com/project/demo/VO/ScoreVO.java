package com.project.demo.VO;

/**
 * @description:
 * @author: YZL
 * @time: 2020/2/9 12:20
 */
public class ScoreVO {
    private Float examScore;

    private Float performanceScore;

    private Float workScore;

    private String totalScore;

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

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }
}
