package member;

import java.util.Scanner;

import javax.naming.ldap.ManageReferralControl;

public class memberManagement {

	Scanner sc = new Scanner(System.in);
	
	MemberDAO mDAO = MemberDAO.getInstance();
	
	public memberManagement() {
		while (true) {
			// 메뉴 출력
			menuPrint();
			// 메뉴 입력
			int menuNo = selectMenu();
			// 각 기능별 실행
			if (menuNo == 1) {
				// 제품등록
				signUp();
			} else {
				exit();
			}
		}
	}
	private void menuPrint() {
		System.out.println("=====================================================");
		System.out.println("1. 회원가입 | 9.종료");
		System.out.println("=====================================================");

	}
	
	private int selectMenu() {
		int menu = 0;
		try {
			menu = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자 형식으로 입력해주세요.");
		}
		return menu;
	}
	private void exit() {
		System.out.println("프로그램을 종료합니다.");
	}

	
	
	//회원가입 -- StockManagement 참조
	public void signUp() {
		
		Member member = new Member();
		System.out.println("======<< 회원가입 >> ======");
		
		System.out.println("아이디 > ");
	    member.setMemberId(sc.nextLine());
	    System.out.println("비밀번호 > ");
	    member.setPassword(sc.nextLine());
	    System.out.println("이름 > ");
	    member.setName(sc.nextLine());
	    System.out.println("연락처 > ");
	    member.setPhnum(Integer.parseInt(sc.nextLine()));
	    
	    mDAO.insert(member);
	    
	    
		
	}
}
