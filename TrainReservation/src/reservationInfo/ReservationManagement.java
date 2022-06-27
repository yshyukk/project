package reservationInfo;

import java.util.Scanner;


public class ReservationManagement{

	Scanner sc = new Scanner(System.in);

	ResDAO rDAO = ResDAO.getInstance();

	public ReservationManagement(){
		while (true) {
			// 메뉴 출력
			menuPrint();
			// 메뉴 입력
			int menuNo = selectMenu();
			// 각 기능별 실행
			if (menuNo == 1) {
					//예약
			} else if (menuNo == 2){
				// 예약정보 조회
				
			}else if ( menuNo == 9){
				// exit();
			}
		}
	}

	private void menuPrint() {
		System.out.println("=====================================================");
		System.out.println("1. 예약정보 조회 | 9.뒤로가기");
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
}
