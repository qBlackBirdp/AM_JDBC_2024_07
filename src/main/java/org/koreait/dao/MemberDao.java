package org.koreait.dao;

import org.koreait.Util.DBUtil;
import org.koreait.Util.SecSql;

import java.sql.Connection;

public class MemberDao {



    public boolean isLoginIdDup(Connection conn, String loginId) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE loginId = ?;", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }
}
