package org.koreait.test;

import org.koreait.Article.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCDeleteTest {
    public static void main(String[] args) {
        Connection conn = null;
        Statement pstmt = null;
        ResultSet rs = null;
        List<Article> articles = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "1234");
            System.out.println("연결 성공!");

            String sql = "SELECT * ";
            sql += "FROM article ";
            sql += "ORDER BY id DESC";

            System.out.println(sql);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String regDate = rs.getString("regDate");
                String updateDate = rs.getString("updateDate");
                String title = rs.getString("title");
                String body = rs.getString("body");
                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패" + e);
        } catch (SQLException e) {
            System.out.println("에러 : " + e);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }if (articles.isEmpty()) {
                System.out.println("게시글이 없습니다");
            }
            System.out.println("   번호    /    제목     ");
            for (Article article : articles) {
                System.out.printf("     %d      /    %s    \n", article.getId(), article.getTitle());
            }
        }
    }
}