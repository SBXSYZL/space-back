package com.project.demo.DO;

import java.util.Date;

public class WorkSubmitDO {
    private Integer submitId;

    private Integer userId;

    private Integer courseId;

    private Byte submitStatus;

    private Integer workId;

    private String content;

    private Date submitDate;

    private Integer score;

    public Integer getSubmitId() {
        return submitId;
    }

    public void setSubmitId(Integer submitId) {
        this.submitId = submitId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Byte getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(Byte submitStatus) {
        this.submitStatus = submitStatus;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}