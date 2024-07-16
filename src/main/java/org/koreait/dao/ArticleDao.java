package org.koreait.dao;

import org.koreait.Util.DBUtil;
import org.koreait.Util.SecSql;
import org.koreait.container.Container;
import org.koreait.dto.Article;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.koreait.container.Container.articleService;

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

    public List<Article> getArticles()  {
        SecSql sql = new SecSql();

        sql.append("SELECT a.*, m.`name`");
        sql.append("FROM article a");
        sql.append("INNER JOIN member m on a.memberId = m.id");
        sql.append("ORDER BY id DESC");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(Container.conn, sql);
        List<Article> articles = new ArrayList<>();


        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }

        return articles;
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

    public Article showDetail(int id) {
        SecSql sql = new SecSql();
        sql.append("SELECT a.*, m.`name`");
        sql.append("FROM article a");
        sql.append("INNER JOIN `member` m");
        sql.append("ON a.memberId = m.id");
        sql.append("WHERE a.id = ?;", id);

        Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);

        if(articleMap == null) {
            return null;
        }

        return new Article(articleMap);
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
