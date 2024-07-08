package org.koreait;

import java.sql.*;

public class JDBCSelectTest {
    public static void main(String[] args) {
        Connection conn = null;
        Statement pstmt = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "1234");
            System.out.println("연결 성공!");

            pstmt = conn.createStatement();
            //4. SQL 처리하고 결과 ResultSet에 받아오기
            String sql = "SELECT * FROM article";
            ResultSet rs = pstmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("title"));
                System.out.println(rs.getString("body"));
                System.out.println(rs.getString("regDate"));
                System.out.println("=".repeat(50));
            }

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
}