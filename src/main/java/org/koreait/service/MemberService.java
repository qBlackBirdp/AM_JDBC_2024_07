package org.koreait.service;

import org.koreait.dao.MemberDao;

import java.sql.Connection;

public class MemberService {

    MemberDao memberDao;

    public MemberService(Connection conn) {
        this.memberDao = new MemberDao(conn);
    }

    public boolean isLoginIdDup(String loginId) {
        return memberDao.isLoginIdDup(loginId);
    }

    public int doJoin(String loginId, String loginPw, String name) {
        return memberDao.doJoin(loginId, loginPw, name);
    }

    public boolean doLogin(String loginId, String loginPw) {

        return memberDao.doLogin(loginId, loginPw);
    }
}
