package org.koreait.controller;

import org.koreait.container.Container;
import org.koreait.dto.Article;
import org.koreait.service.ArticleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ArticleController {

    private ArticleService articleService;

    public ArticleController() {
        this.articleService = Container.articleService;
    }

    public void doWrite() {
        if(Container.session.loginedMember == null){
            System.out.println("로그인 안하면 글 못써");
        }else {
            System.out.println("==글쓰기==");
            System.out.print("제목 : ");
            String title = Container.sc.nextLine();
            System.out.print("내용 : ");
            String body = Container.sc.nextLine();

            int id = articleService.doWrite(title, body);

            System.out.println(id + "번 글이 생성되었습니다");
        }
    }

    public void showList() {

        System.out.println("==목록==");

        List<Article> articles = new ArrayList<>();

        List<Map<String, Object>> articleListMap = articleService.showList();

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }

        if (articles.isEmpty()) {
            System.out.println("게시글이 없습니다");
            return;
        }

        System.out.println("  번호  /   제목  ");
        for (Article article : articles) {
            System.out.printf("  %d     /   %s   \n", article.getId(), article.getTitle());
        }
    }

    public void doDelete(String cmd) {
        if(Container.session.loginedMember == null){
            System.out.println("로그인 안되어 있어.");
            return;
        }
        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해");
            return;
        }

        boolean canAccess = articleService.canAccess(id);

        int deleteId = articleService.isExistId(id);

        if (deleteId == 0) {
            System.out.println(id + "번 게시물 없어.");
            return;
        }
        if(canAccess){
                articleService.doDelete(id);
                System.out.println(id + "번 글이 삭제되었습니다.");
        } else System.out.println("삭제 권한 없어.");

    }

    public void doModify(String cmd) {
        if(Container.session.loginedMember == null){
            System.out.println("로그인 안되어 있어.");
            return;
        }
        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해");
            return;
        }

        boolean canAccess = articleService.canAccess(id);
        int articleId = articleService.isExistId(id);

        if (articleId == 0) {
            System.out.println(id + "번 게시물 없어.");
            return;
        }
        if (canAccess) {
            System.out.println("==수정==");
            System.out.print("새 제목 : ");
            String newTitle = Container.sc.nextLine().trim();
            System.out.print("새 내용 : ");
            String newBody = Container.sc.nextLine().trim();

            articleService.doUpdate(newTitle, newBody, id);

            System.out.println(id + "번 글이 수정되었습니다.");
        } else System.out.println("수정 권한 없어.");
    }

    public void showDetail(String cmd) {
        int id = 0;
        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해");
            return;
        }

        int articleId = articleService.isExistId(id);

        if (articleId == 0) {
            System.out.println(id + "번 게시물 없어.");
        } else {
            Map<String, Object> articleListMap = articleService.showDetail(id);

            Article article = new Article(articleListMap);

            System.out.println("번호 : " + article.getId());
            System.out.println("제목 : " + article.getTitle());
            System.out.println("내용 : " + article.getBody());
            System.out.println("작성자 : " + article.getName());
            System.out.println("작성시간 : " + article.getRegDate());
            System.out.println("수정시간 : " + article.getUpdateDate());
        }
    }
}
