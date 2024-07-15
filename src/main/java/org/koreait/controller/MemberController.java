package org.koreait.controller;

import org.koreait.service.MemberService;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController {
    Connection conn;
    Scanner sc;

    MemberService memberService;

    protected static int loginedMember;

    public MemberController(Scanner sc, Connection conn) {
        this.sc = sc;
        this.conn = conn;
        this.memberService = new MemberService(conn);
    }

    public void doJoin() {
        String loginId = null;
        String loginPw = null;
        String loginPwConfirm = null;
        String name = null;

        System.out.println("==회원가입==");
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();

            if (loginId.isEmpty() || loginId.contains(" ")) {
                System.out.println("아이디 똑바로 써");
                continue;
            }

            boolean isLoindIdDup = memberService.isLoginIdDup(loginId);

            if (isLoindIdDup) {
                System.out.println(loginId + "는(은) 이미 사용중");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine().trim();

            if (loginPw.isEmpty() || loginPw.contains(" ")) {
                System.out.println("비번 똑바로 입력해");
                continue;
            }

            boolean loginPwCheck = true;

            while (true) {
                System.out.print("비밀번호 확인 : ");
                loginPwConfirm = sc.nextLine().trim();

                if (loginPwConfirm.isEmpty() || loginPwConfirm.contains(" ")) {
                    System.out.println("비번 확인 똑바로 써");
                    continue;
                }
                if (!loginPw.equals(loginPwConfirm)) {
                    System.out.println("일치하지 않아");
                    loginPwCheck = false;
                }
                break;
            }
            if (loginPwCheck) {
                break;
            }
        }

        while (true) {
            System.out.print("이름 : ");
            name = sc.nextLine();

            if (name.isEmpty() || name.contains(" ")) {
                System.out.println("이름 똑바로 써");
                continue;
            }
            break;
        }

        int id = memberService.doJoin(loginId, loginPw, name);

        System.out.println(id + "번 회원이 생성되었습니다");
    }

    public void doLogin() {
        if (isLogined()) {
            System.out.println("이미 로그인 되어있음.");
            return;
        }
        int i = 0;

        while (true) {
            System.out.println("== 로그인 ==");
            System.out.print("로그인 아이디 : ");
            String loginId = sc.nextLine().trim();
            System.out.print("비밀번호 : ");
            String loginPw = sc.nextLine().trim();

            boolean isValidUser = memberService.doLogin(loginId, loginPw);
            if (isValidUser) {
                System.out.println("== 로그인 완료 ==");

                loginedMember = memberService.isLogined(loginId);
                System.out.println(loginedMember);
                return;
            } else {
                i++;
                System.out.printf("== 로그인 실패 (로그인 시도 %d, 3번 실패시 out) ==\n", i);
                if (i == 3)  {
                    System.out.println("!!!! OUT OF USER !!!!");
                    return;
                }
            }
        }
    }

    public void doLogout() {
        if (isLogined()) {
            loginedMember = 0;
            System.out.println("== 로그 아웃 완료 ==");
        } else {
            System.out.println("로그인 부터 해.");
        }
    }

    private boolean isLogined() {

        return loginedMember != 0;
    }
}
