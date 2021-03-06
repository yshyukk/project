package member;

import java.util.Scanner;

public class SignUpManagement {

	Scanner sc = new Scanner(System.in);

	MemberDAO mDAO = MemberDAO.getInstance();

	public SignUpManagement() {
		while (true) {
			// 메뉴 출력
			menuPrint();
			// 메뉴 입력
			int menuNo = selectMenu();
			// 각 기능별 실행
			if (menuNo == 1) {
				updateMemberInfo();
			} else {
				back();
				break;
			}
		}
	}

	private void menuPrint() {
		System.out.println("======================");
		System.out.println("1. 회원정보수정 | 9.돌아가기");
		System.out.println("======================");

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

	private void back() {
		System.out.println("이전 메뉴로 돌아갑니다.");
	}

	// 비밀번호, 폰번호 변경
	public void updateMemberInfo() {
		System.out.println("======::::: 회원정보 수정 :::::======");
		// 비밀번호수정
		// MemberId 입력받고
		String MemberId = inputMemberId();

		// 수정할 정보 검색해옴
		Member searchInfo = mDAO.selectPw(MemberId);

		// table정보 불러오기

		if (searchInfo.equals(null)) {
			System.out.println("ID를 확인해주시기 바랍니다.");
			return;
		}
		// 수정할 정보 입력
		searchInfo = inputUpdateInfo(searchInfo);

		mDAO.update(searchInfo);

	}

	private Member inputUpdateInfo(Member member) {
		System.out.println("기존 : " + member.getPassword());
		System.out.println("[ 변경X : 0 ] 변경할 PASSWORD > ");
		String password = sc.nextLine();
		if (!password.equals("0")) {
			member.setPassword(password);
		}
		System.out.println("기존 > " + member.getPhnum());
		System.out.println("[ 변경X : 0 ] 변경할 phNum >");
		int phnum = Integer.parseInt(sc.nextLine());
		if (phnum > -1) {
			member.setPhnum(phnum);
		}
		return member;
	}

	public String inputMemberId() {
		System.out.println("ID  >");
		return sc.nextLine();
	}

}
