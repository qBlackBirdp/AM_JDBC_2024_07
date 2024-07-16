package org.koreait.session;

import org.koreait.container.Container;
import org.koreait.dto.Member;

public class Session {
    public Member loginedMember;
    public int loginedMemberId;

    public Session() {
        loginedMember = null;
        loginedMemberId = -1;
    }

    public void logout() {
        Container.session.loginedMemberId = -1;
        Container.session.loginedMember = null;
    }

    public void login(Member member) {
        Container.session.loginedMember = member;
        Container.session.loginedMemberId = member.getId();
    }

    public boolean isLogined() {
        return Container.session.loginedMember != null;
    }
}
