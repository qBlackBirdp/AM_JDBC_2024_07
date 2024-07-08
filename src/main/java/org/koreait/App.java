package org.koreait;

import java.sql.*;
import java.util.Scanner;

public class App {
    static Scanner sc;


    static Connection conn = null; // DB 접속하는 객체
    static Statement pstmt = null; // SQL 전송하는 객체
    static ResultSet rs = null; // 결과 받아오는 객체
    static String url;
    static String user;
    static String pass;

    public App() {
        sc = new Scanner(System.in);
        conn = null;
        pstmt = null;
        rs = null;
        url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
        user = "root";
        pass = "1234";
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

            switch (cmd) {
                case "article write":
                    doWrite();
                    break;
                case "article list":
                    showList();
                    break;
//                case "article delete":
//                    doDelete();
//                    break;
                default:
                    System.out.println("명령어오류");
                    break;
            }
        }
    }


    private static void doWrite() {
        System.out.println("== 게시물 작성 ==");
//        System.out.print("제목 : ");
//        String title = sc.nextLine();
//        System.out.print("내용 : ");
//        String body = sc.nextLine();=

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
        }finally {
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

            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("title"));
                System.out.println(rs.getString("body")); // 문자열로 리턴
                System.out.println("=".repeat(40));
            }

        } catch (Exception e) {
            System.out.println("접속 오류");
        }finally {
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

}