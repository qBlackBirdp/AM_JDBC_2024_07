package org.koreait.dao;

import org.koreait.Util.DBUtil;
import org.koreait.Util.SecSql;
import org.koreait.container.Container;
import org.koreait.dto.Member;

import java.util.List;
import java.util.Map;

public class ArticleDao {

    Member member;

    public int doWrite(String title, String body) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("title = ?,", title);
        sql.append("`body`= ?,", body);
        sql.append("memberId = ?;", member.getId());

        return DBUtil.insert(Container.conn, sql);
    }

    public List<Map<String, Object>> showList() {
        SecSql sql = new SecSql();

        sql.append("SELECT *");
        sql.append("FROM article");
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
        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW(),");
        if (!newTitle.isEmpty()) {
            sql.append("title = ?,", newTitle);
        }
        if (!newBody.isEmpty()) {
            sql.append("`body`= ?", newBody);
        }
        sql.append("WHERE id = ?;", id);

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
}
