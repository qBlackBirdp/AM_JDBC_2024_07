package org.koreait;

import org.koreait.Article.Article;
import org.koreait.Util.DBUtil;
import org.koreait.Util.SecSql;
import org.koreait.controller.ArticleController;
import org.koreait.controller.MemberController;
import org.koreait.exception.SQLErrorException;
import org.koreait.member.Member;

import java.sql.*;
import java.util.*;

public class App {

    public void run() {
        System.out.println("==프로그램 시작==");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("명령어 > ");
            String cmd = sc.nextLine().trim();

            Connection conn = null;

            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";

            try {
                conn = DriverManager.getConnection(url, "root", "1234");

                int actionResult = action(conn, sc, cmd);

                if (actionResult == -1) {
                    System.out.println("==프로그램 종료==");
                    sc.close();
                    break;
                }

            } catch (SQLException e) {
                System.out.println("에러 1 : " + e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int action(Connection conn, Scanner sc, String cmd) {

        if (cmd.equals("exit")) {
            return -1;
        }

        MemberController memberController = new MemberController(sc, conn);
        ArticleController articleController = new ArticleController();

        if (cmd.equals("member join")) {
            memberController. doJoin();

        } else if (cmd.equals("article list")) {
            System.out.println("==목록==");


            List<Article> articles = new ArrayList<>();

            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM article");
            sql.append("ORDER BY id DESC");

            List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

            for (Map<String, Object> articleMap : articleListMap) {
                articles.add(new Article(articleMap));
            }

            if (articles.size() == 0) {
                System.out.println("게시글이 없습니다");
                return 0;
            }

            System.out.println("  번호  /   제목  ");
            for (Article article : articles) {
                System.out.printf("  %d     /   %s   \n", article.getId(), article.getTitle());
            }
        } else if (cmd.startsWith("article delete")) {

            int id = 0;

            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("번호는 정수로 입력해");
                return 0;
            }

            SecSql sql = new SecSql();
            sql.append("SELECT COUNT(*) FROM article WHERE id = ?;", id);
            int deleteId = DBUtil.selectRowIntValue(conn, sql);

            if (deleteId == 0) {
                System.out.println(id + "번 게시물 없어.");
            } else {
                SecSql sql2 = new SecSql();
                sql2.append("DELETE FROM article WHERE id = ?;", id);
                DBUtil.delete(conn, sql2);
                System.out.println(id + "번 글이 삭제되었습니다.");
            }

        } else if (cmd.startsWith("article modify")) {

            int id = 0;

            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("번호는 정수로 입력해");
                return 0;
            }
            SecSql sql = new SecSql();
            sql.append("SELECT COUNT(*) FROM article WHERE id = ?;", id);
            int articleId = DBUtil.selectRowIntValue(conn, sql);

            if (articleId == 0) {
                System.out.println(id + "번 게시물 없어.");
            } else {
                System.out.println("==수정==");
                System.out.print("새 제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("새 내용 : ");
                String body = sc.nextLine().trim();
                SecSql sql2 = new SecSql();
                sql2.append("UPDATE article");
                sql2.append("SET updateDate = NOW(),");
                if (!title.isEmpty()) {
                    sql2.append("title = ?,", title);
                }
                if (!body.isEmpty()) {
                    sql2.append("`body`= ?", body);
                }
                sql2.append("WHERE id = ?;", id);

                DBUtil.update(conn, sql2);
                System.out.println(id + "번 글이 수정되었습니다.");
            }
        } else if (cmd.startsWith("article detail")) {
            int id = 0;
            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("번호는 정수로 입력해");
                return 0;
            }
            SecSql sql = new SecSql();
            sql.append("SELECT COUNT(*) FROM article WHERE id = ?;", id);
            int articleId = DBUtil.selectRowIntValue(conn, sql);
            if (articleId == 0) {
                System.out.println(id + "번 게시물 없어.");
            } else {

                SecSql sql2 = new SecSql();
                sql2.append("SELECT * FROM article WHERE id = ?;", id);
                Map<String, Object> articleListMap = DBUtil.selectRow(conn, sql2);

//                if (articleListMap.isEmpty()) {
//                    System.out.println(id + "번 게시물 없어");
//                    return 0;
//                }

                Article article = new Article(articleListMap);

                System.out.println("번호 : " + article.getId());
                System.out.println("제목 : " + article.getTitle());
                System.out.println("내용 : " + article.getBody());
                System.out.println("작성시간 : " + article.getRegDate());
                System.out.println("수정시간 : " + article.getUpdateDate());
            }


        }
        return 0;
    }
}