package org.koreait.service;

import org.koreait.container.Container;
import org.koreait.dao.ArticleDao;
import org.koreait.dto.Article;

import java.util.List;
import java.util.Map;

public class ArticleService {

    private ArticleDao articleDao;

    public ArticleService() {
        this.articleDao = Container.articleDao;
    }


    public int doWrite(String title, String body) {
        return articleDao.doWrite(title, body);
    }

    public List<Article> getArticles() {
        return articleDao.getArticles();
    }

    public int isExistId(int id) {
        return articleDao.isExistId(id);
    }

    public void doDelete(int id) {
        articleDao.doDelete(id);
    }

    public void doUpdate(String newTitle, String newBody, int id) {
        articleDao.doUpdate(newTitle, newBody, id);
    }

    public Article showDetail(int id) {
        return articleDao.showDetail(id);
    }

    public boolean canAccess(int id) {
        return articleDao.canAccess(id);
    }
}
