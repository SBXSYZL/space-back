package com.project.demo.DO;

import java.util.Date;

public class WorkDO {
    private Integer workId;

    private Integer courseId;

    private String workName;

    private Date deadline;

    private Integer submitCnt;

    private Integer authorId;

    private String workDesc;

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getSubmitCnt() {
        return submitCnt;
    }

    public void setSubmitCnt(Integer submitCnt) {
        this.submitCnt = submitCnt;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc == null ? null : workDesc.trim();
    }
}