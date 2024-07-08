package org.koreait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner sc;
    static int lastId;
    static List<Article> articles;

    public App() {
        sc = new Scanner(System.in);
        lastId = 1;
        articles = new ArrayList<>();
    }

    public static void run() {
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
                case "article delete":
                    doDelete();
                    break;
                default:
                    System.out.println("명령어오류");
                    break;
            }
        }
    }

    private static void doDelete() {
        Article found = foundArticleId();
    }

    private static Article foundArticleId() {
        for (Article a : articles) {
            if (a.getId() == lastId) {
                return a;
            }
        }
        return null;
    }

    private static void showList() {
        if (articles.isEmpty()) {
            System.out.println("게시물 없어");
        } else {
            System.out.println("번호      /       제목      /       내용");
            System.out.println("=".repeat(50));
            for (int i = articles.size() - 1; i >= 0; i--) {
                Article article = articles.get(i);
                System.out.printf("%d       /       %s      /       %s \n", article.getId(), article.getTitle(), article.getBody());
            }

        }

    }

    private static void doWrite() {
        System.out.println("== 게시물 작성 ==");
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        int id = lastId++;

        articles.add(new Article(id, title, body));
        System.out.printf("%d번 게시물 작성되었습니다.\n", id);
    }

}
