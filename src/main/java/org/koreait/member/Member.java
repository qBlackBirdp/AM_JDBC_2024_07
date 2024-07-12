package org.koreait.member;

import java.util.Map;

public class Member {

    private int id;
    private String regDate;
    private String memberId;
    private String memberPw;

    public Member(int id, String memberId, String memberPw, String regDate) {
        this.id = id;
        this.regDate = regDate;
        this.memberId = memberId;
        this.memberPw = memberPw;
    }

    public Member(Map<String, Object> memberMap) {
        this.id = (int) memberMap.get("id");
        this.regDate = (String) memberMap.get("regDate");
        this.memberId = (String) memberMap.get("memberId");
        this.memberPw = (String) memberMap.get("memberPw");
    }
}
