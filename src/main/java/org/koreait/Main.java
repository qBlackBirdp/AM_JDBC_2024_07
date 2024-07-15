package org.koreait;

import org.koreait.exception.SQLErrorException;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        new App().run();
//        try {
//        } catch (SQLErrorException e) {
//            System.err.println(e.getMessage());
//            e.getOrigin().printStackTrace();
//        }
    }
}