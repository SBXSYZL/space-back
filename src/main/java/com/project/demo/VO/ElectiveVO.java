package com.project.demo.VO;

public class ElectiveVO {
    private Integer userId;

    private String nickName;

    private double progress;

    private Integer courseId;

    private Float examScore;

    private Float performanceScore;

    private Float workScore;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

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
}
