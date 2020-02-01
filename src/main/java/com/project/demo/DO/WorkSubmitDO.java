package com.project.demo.DO;

public class WorkSubmitDO {
    private Integer submitId;

    private Integer userId;

    private Integer courseId;

    private Integer lessonId;

    private Byte submitStatus;

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

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Byte getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(Byte submitStatus) {
        this.submitStatus = submitStatus;
    }
}