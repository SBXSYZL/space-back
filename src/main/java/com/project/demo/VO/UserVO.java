package com.project.demo.VO;

/**
 * @description:
 * @author: YZL
 * @time: 2020/2/2 20:28
 */
public class UserVO {
    private Integer userId;

    private String nickName;

    private String account;

    private String tel;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
