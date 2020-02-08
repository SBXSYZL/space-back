package com.project.demo.VO;

import java.util.Date;

/**
 * @description:
 * @author: YZL
 * @time: 2020/2/8 14:08
 */
public class WorkSubmitVO {
    private Integer submitId;

    private Integer userId;

    private String userName;

    private Byte submitStatus;

    private String content;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Byte getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(Byte submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
