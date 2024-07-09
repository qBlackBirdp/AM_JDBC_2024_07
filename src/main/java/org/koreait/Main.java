package org.koreait;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            new App().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}