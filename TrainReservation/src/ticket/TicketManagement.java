package ticket;

import java.util.Scanner;

public class TicketManagement {

	Scanner sc = new Scanner(System.in);
	
	TicketDAO tDao = TicketDAO.getInstance();
	
	public TicketManagement() {
		while (true) {
			// 메뉴 출력
			menuPrint();
			// 메뉴 입력
			int menuNo = selectMenu();
			// 각 기능별 실행
			if (menuNo == 1) {
				// 제품등록
				allTicketInfo();
				
			}else if(menuNo == 2){
				oneTicketInfo();
			}else if (menuNo == 3) {
				inserticketInfo();
			}else {
				exit();
			}
		}
	}
	private void menuPrint() {
		System.out.println("=====================================================");
		System.out.println("1. 티켓정보확인 | 2.티켓전체조회 | 3. 티켓정보입력 | 9.종료");
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
	public void allTicketInfo() {
		
	}
	public void oneTicketInfo() {
		
	}
	public void inserticketInfo() {
		Ticket ticket = new Ticket();
	
		System.out.println("ticket_id> ");
		ticket.setTicketId(Integer.parseInt(sc.nextLine()));
		System.out.println("timetable_num> ");
		ticket.setTicketId(Integer.parseInt(sc.nextLine()));
		System.out.println("train_sector> ");
		ticket.setTicketId(Integer.parseInt(sc.nextLine()));
		System.out.println("seat_num> ");
		ticket.setTicketId(Integer.parseInt(sc.nextLine()));
		System.out.println("price> ");
		ticket.setTicketId(Integer.parseInt(sc.nextLine()));
	
		tDao.insert(ticket);
	}
	
}
