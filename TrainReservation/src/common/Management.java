package common;

import java.util.Scanner;

import member.MemberDAO;
import member.SignUpManagement;
import reservationInfo.ResDAO;
import ticket.TicketDAO;
import ticket.TicketManagement;
import timeTable.SearchTableInfoManagement;
import timeTable.TimeTableDAO;
import timeTable.TimeTableManagement;

public class Management {
//필드
	protected Scanner sc = new Scanner(System.in);
	protected MemberDAO mDao = MemberDAO.getInstance();
	protected ResDAO rDao = ResDAO.getInstance();
	protected TicketDAO tDao = TicketDAO.getInstance();
	protected TimeTableDAO tableDao = TimeTableDAO.getInstance();

	public void run() {
		boolean role = selectRole();
		while (true) {
			// menuPrint에 role??? -> 추가하는게 달라지기 때문에
			//
			menuPrint(role);
			int menuNo = menuSelect();

			if (menuNo == 1 && role) {
				// 1. 테이블 관리
				new TimeTableManagement();
			} else if (menuNo == 2) {
				// 2. 테이블 조회
				new SearchTableInfoManagement();
			} else if (menuNo == 3) {
				// 3. 회원정보 수정
				new SignUpManagement().updateMemberInfo();
			} else if (menuNo == 4) {
				// 4. 예약정보 조회
				new TicketManagement();
			}else if (menuNo == 5) {
				// 5. 예매
				new TicketManagement();
			} else if (menuNo == 9) {
				// 뒤로가기
				back();
				break;
			} else {
				// 입력오류
				showInputError();
			}
		}
	}

	// 메소드
	protected void menuPrint(boolean role) {

		System.out.println("=============================================================================================");		
		System.out.println("1. TimeTable 관리 | 2. TimeTable 조회 | 3. 회원정보 수정 | 4. 발권조회 | 5. 예매 및 티켓관리 |  9.뒤로가기");
		System.out.println("=============================================================================================");
	}

	protected int menuSelect() {
		int menuNo = 0;
		try {
			menuNo = Integer.parseInt(sc.nextLine());

		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력하세요");
		}
		return menuNo;
	}

	protected void back() {
		System.out.println("이전 메뉴로 돌아갑니다.");
	}

	protected void showInputError() {
		System.out.println("메뉴에서 입력해주시기 바랍니다.");
	}

	public boolean selectRole() {
		// 관리자일때만 role값이 true가 될겁니다.
		int memberRole = Login.getLoginInfo().getMemberRole();
		if (memberRole == 0) {
			return true;
		} else {
			return false;
		}

	}

}
