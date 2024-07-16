package org.koreait.dao;

import org.koreait.Util.DBUtil;
import org.koreait.Util.SecSql;
import org.koreait.container.Container;


import java.util.List;
import java.util.Map;

public class ArticleDao {

    public int doWrite(String title, String body) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("title = ?,", title);
        sql.append("`body`= ?,", body);
        sql.append("memberId = ?;", Container.session.loginedMember.getId());

        return DBUtil.insert(Container.conn, sql);
    }

    public List<Map<String, Object>> showList() {
        SecSql sql = new SecSql();

        sql.append("SELECT a.*, m.`name`");
        sql.append("FROM article a");
        sql.append("INNER JOIN member m on a.memberId = m.id");
        sql.append("ORDER BY id DESC");

        return DBUtil.selectRows(Container.conn, sql);
    }

    public int isExistId(int id) {
        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(*) FROM article WHERE id = ?;", id);

        return DBUtil.selectRowIntValue(Container.conn, sql);
    }

    public void doDelete(int id) {
        SecSql sql = new SecSql();
        sql.append("DELETE FROM article WHERE id = ?;", id);

        DBUtil.delete(Container.conn, sql);
    }

    public void doUpdate(String newTitle, String newBody, int id) {
        SecSql sql = new SecSql();
        sql.append("UPDATE article a");
        sql.append("INNER JOIN `member` m");
        sql.append("SET a.updateDate = NOW(),");
        if (!newTitle.isEmpty()) {
            sql.append("a.title = ?,", newTitle);
        }
        if (!newBody.isEmpty()) {
            sql.append("a.`body`= ?", newBody);
        }
        sql.append("WHERE a.id = ?", id);
        sql.append("AND m.id = ?;", Container.session.loginedMember.getId());

        DBUtil.update(Container.conn, sql);
    }

    public Map<String, Object> showDetail(int id) {
        SecSql sql = new SecSql();
        sql.append("SELECT a.*, m.`name`");
        sql.append("FROM article a");
        sql.append("INNER JOIN `member` m");
        sql.append("ON a.memberId = m.id");
        sql.append("WHERE a.id = ?;", id);

        return DBUtil.selectRow(Container.conn, sql);
    }

    public boolean canAccess(int articleId) {
        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(*) > 0");  // 권한이 있는지 확인하는 부울 값을 반환하기 위해 COUNT 사용
        sql.append("FROM article a");
        sql.append("INNER JOIN `member` m ON a.memberId = m.id");
        sql.append("WHERE a.id = ?", articleId);
        sql.append("AND m.id = ?", Container.session.loginedMember.getId());

        return DBUtil.selectRowBooleanValue(Container.conn, sql);
    }
}
