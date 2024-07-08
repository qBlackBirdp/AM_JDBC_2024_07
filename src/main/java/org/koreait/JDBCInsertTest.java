package org.koreait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "1234");
            System.out.println("연결 성공!");

            String sql = "INSERT INTO article\n" +
                    "    SET regDate = now(),\n" +
                    "        updateDate = now(),\n" +
                    "        title = CONCAT('제목1', SUBSTRING(RAND() * 1000 From 1 For 2)),\n" +
                    "        `body` = CONCAT('내용', SUBSTRING(RAND() * 1000 From 1 For 2));";

            pstmt = conn.prepareStatement(sql);

            int affectedRows = pstmt.executeUpdate(); //무슨열에 적용되었는지 알아내는 코드

            System.out.println("affectedRows " + affectedRows);

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