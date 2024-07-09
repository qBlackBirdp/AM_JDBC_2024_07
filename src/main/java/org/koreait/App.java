package org.koreait;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner sc;

    static Connection conn = null; // DB 접속하는 객체
    static Statement pstmt = null; // SQL 전송하는 객체
    static ResultSet rs = null; // 결과 받아오는 객체
    static String url;
    static String user;
    static String pass;
    static String id;

    static List<Article> articles;

    public App() {
        sc = new Scanner(System.in);
        conn = null;
        pstmt = null;
        rs = null;
        url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
        user = "root";
        pass = "1234";
        articles = new ArrayList<>();
    }

    public static void run() throws SQLException {
        System.out.println("== 프로그램 시작 ==");

        while (true) {
            System.out.print("명령어 : ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                System.out.println("== 프로그램 종료 ==");
                return;
            } else if (cmd.isEmpty()) {
                System.out.println("명령어 입력해");
            }

            String[] str = cmd.split(" ");
            String first = str[0];
            String second = str.length > 1 ? str[1] : "";
            id = str.length > 2 ? str[2] : "";

            switch (cmd) {
                case "article write":
                    doWrite();
                    break;
                case "article list":
                    showList();
                    break;
                case "article delete":
                    doDelete();
                    break;
                default:
                    if (cmd.startsWith("article delete")) {
                        doDelete();
                    } else if (cmd.startsWith("article modify")) {
                        showModify();
                    } else System.out.println("명령어오류");
                    break;
            }
        }
    }

    private static void showModify() {
        System.out.println("== 게시물 수정 ==");
        System.out.print("제목 : ");
        String newTitle = sc.nextLine().trim();
        System.out.print("내용 : ");
        String newBody = sc.nextLine().trim();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "1234");
            System.out.println("연결 성공!");

            String sql = "UPDATE article ";
            sql += "SET updateDate = NOW()";
            if (newTitle.length() > 0) {
                sql += " , title = '" + newTitle + "'";
            }
            if (newBody.length() > 0) {
                sql += " , `body` = '" + newBody + "'";
            }
            sql += " WHERE id = " + id + ";";

            System.out.println(sql);

            pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate(sql);

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패" + e);
        } catch (SQLException e) {
            System.out.println("에러 : " + e);
        } finally {
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
            }
        }
        System.out.println(id + "번 글이 수정되었습니다.");
    }


    private static void doDelete() {

        System.out.println("== 게시물 삭제 ==");
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("연결 성공!");

            pstmt = conn.createStatement();
            //4. SQL 처리하고 결과 ResultSet에 받아오기

            String sql = "SELECT id FROM article WHERE id = " + "id" + ";";
            ResultSet rs = pstmt.executeQuery(sql);


            if (!rs.next()) {
                System.out.printf("%d번 게시물 없어.", id);
                return;
            } else sql = "DELETE from article\n" +
                    "where id = " + id + ";";
            pstmt.executeQuery(sql);

            System.out.printf("%s번 게시물 삭제\n", id);


        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패" + e);
        } catch (SQLException e) {
            System.out.println("에러 : " + e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
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
        }
    }


    private static void doWrite() {
        System.out.println("== 게시물 작성 ==");
        try {
            // 1. 드라이버 세팅
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. Connection 획득
            conn = DriverManager.getConnection(url, user, pass);

            //3. Statement 생성
            pstmt = conn.createStatement();

            System.out.print("제목 : ");
            String title = sc.nextLine();
            System.out.print("내용 : ");
            String body = sc.nextLine();

            //4. SQL 처리하고 결과 ResultSet에 받아오기
            String sql = "INSERT INTO article SET regDate = now(),\n" +
                    "        updateDate = now(), title = '" + title + "', body = '" + body + "'";
            pstmt.executeUpdate(sql);
            System.out.println("게시물 등록이 완료되었습니다.");
            // 조회 결과 있는 거 -> executeQuery(sql);
            // 조회 결과 없는 거 -> executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("접속 시도중 문제 발생!!");
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
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
        }
    }


    private static void showList() {
        // board db의 article table에서 데이터를 꺼내와 출력

        // 자동임포트 : alt + enter


        try {
            // 1. 드라이버 세팅
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. Connection 획득
            conn = DriverManager.getConnection(url, user, pass);

            //3. Statement 생성
            pstmt = conn.createStatement();
            //4. SQL 처리하고 결과 ResultSet에 받아오기
            String sql = "SELECT * FROM article ORDER BY id desc";
            rs = pstmt.executeQuery(sql);

            articles.clear();

            while (rs.next()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String body = rs.getString("body");
                    String regDate = rs.getString("regDate");
                    Article article = new Article(id, title, body, regDate, body);
                    articles.add(article);
                }
            }
            if (articles.isEmpty()) {
                System.out.println("게시물 없어.");
            } else {
                for (Article article : articles) {
                    System.out.println(article.getId() + article.getTitle());
                }
            }
        } catch (Exception e) {
            System.out.println("접속 오류");
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
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
            }
        }
    }

}