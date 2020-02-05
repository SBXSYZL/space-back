package com.project.demo.VO;

import java.util.Date;

/**
 * @description:
 * @author: YZL
 * @time: 2020/2/3 16:45
 */
public class MsgVO {
    private Integer msgId;

    private String authorName;

    private Date postDate;

    private String content;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
