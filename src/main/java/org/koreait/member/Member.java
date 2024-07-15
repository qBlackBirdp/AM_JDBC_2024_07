package org.koreait.member;

import java.util.Map;

public class Member {

    private int id;
    private String regDate;
    private String updateDate;
    private String loginId;
    private String loginPw;

    public Member(int id, String regDate, String updateDate, String loginId, String loginPw) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.loginId = loginId;
        this.loginPw = loginPw;
    }

    public Member(Map<String, Object> memberMap) {
        this.id = (int) memberMap.get("id");
        this.regDate = (String) memberMap.get("regDate");
        this.updateDate = (String) memberMap.get("updateDate");
        this.loginId = (String) memberMap.get("loginId");
        this.loginPw = (String) memberMap.get("loginPw");
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", regDate='" + regDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", loginId='" + loginId + '\'' +
                ", loginPw='" + loginPw + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPw() {
        return loginPw;
    }

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }
}
