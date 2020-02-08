package com.project.demo.DO;

import java.util.Date;

public class CourseDO {
    private Integer courseId;

    private String courseName;

    private Date courseDeadline;

    private Integer authorId;

    private Integer schedule;

    private Integer completed;

    private String courseDesc;

    private Integer stuCnt;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public Date getCourseDeadline() {
        return courseDeadline;
    }

    public void setCourseDeadline(Date courseDeadline) {
        this.courseDeadline = courseDeadline;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc == null ? null : courseDesc.trim();
    }

    public Integer getStuCnt() {
        return stuCnt;
    }

    public void setStuCnt(Integer stuCnt) {
        this.stuCnt = stuCnt;
    }
}