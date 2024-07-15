package org.koreait.controller;


import org.koreait.container.Container;
import org.koreait.dto.Member;
import org.koreait.service.MemberService;

public class MemberController {

    MemberService memberService;

    public MemberController() {
        this.memberService = Container.memberService;
    }

    public void doJoin() {
        String loginId = null;
        String loginPw = null;
        String loginPwConfirm = null;
        String name = null;

        System.out.println("==회원가입==");
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = Container.sc.nextLine().trim();

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
            loginPw = Container.sc.nextLine().trim();

            if (loginPw.isEmpty() || loginPw.contains(" ")) {
                System.out.println("비번 똑바로 입력해");
                continue;
            }

            boolean loginPwCheck = true;

            while (true) {
                System.out.print("비밀번호 확인 : ");
                loginPwConfirm = Container.sc.nextLine().trim();

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
            name = Container.sc.nextLine();

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
        if (Container.session.loginedMemberId != -1) {
            System.out.println("이미 로그인 되어있음.");
            return;
        }
        String loginId = null;
        String loginPw = null;

        System.out.println("==로그인==");
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = Container.sc.nextLine().trim();

            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디 똑바로 써");
                continue;
            }

            boolean isLoindIdDup = memberService.isLoginIdDup(loginId);

            if (isLoindIdDup == false) {
                System.out.println(loginId + "는(은) 없어");
                continue;
            }

            break;
        }

        Member member = memberService.getMemberByLoginId(loginId);

        int tryMaxCount = 3;
        int tryCount = 0;

        while (true) {
            if (tryCount >= tryMaxCount) {
                System.out.println("비번 다시 확인하고 시도해");
                break;
            }

            System.out.print("비밀번호 : ");
            loginPw = Container.sc.nextLine().trim();

            if (loginPw.isEmpty() || loginPw.contains(" ")) {
                tryCount++;
                System.out.println("비번 똑바로 입력해");
                continue;
            }
            if (!member.getLoginPw().equals(loginPw)) {
                tryCount++;
                System.out.println("일치하지 않아");
                continue;
            }

            Container.session.loginedMember = member;
            Container.session.loginedMemberId = member.getId();

            System.out.println(member.getName() + "님 환영합니다");
            break;
        }
    }


    public void doLogout() {
        if(Container.session.loginedMember != null) {
            System.out.println("== 로그 아웃 완료 ==");
            Container.session.loginedMemberId = -1;
            Container.session.loginedMember = null;
        }else System.out.println("로그인 부터 해.");

    }

    public void showMemberProfile() {
        if (Container.session.loginedMember == null) {
            System.out.println("로그인 되어있지 않음.");
        } else {
            System.out.println("번호 : " + Container.session.loginedMember.getId());
            System.out.println("아이디 : " + Container.session.loginedMember.getLoginId());
            System.out.println("이름 : " + Container.session.loginedMember.getName());
            System.out.println("가입 날짜 : " + Container.session.loginedMember.getRegDate());
            System.out.println("회원정보 수정 날짜 : " + Container.session.loginedMember.getUpdateDate());
        }
    }
}

