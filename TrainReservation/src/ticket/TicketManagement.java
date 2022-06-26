package ticket;

import java.util.Scanner;

import common.Management;


public class TicketManagement extends Management {

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
				// 예매
				reservationTicket();
			} else if (menuNo == 2) {
				// 예매한 티켓 조회
				oneTicketInfo();
			} else if (menuNo == 3) {
				// 티켓취소
				deleteTicket();
			} else if (menuNo == 4) {
				exit();
			}
		}
	}

	private void menuPrint() {
		System.out.println("=====================================================");
		System.out.println("1. 예매 | 2.예매한 티켓 확인 | 3. 예매취소 | 9.종료");
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

	public void reservationTicket() {
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

	public void oneTicketInfo() {
		// 티켓_id로 티켓조회하기
		int ticketId = inputTicketId();

		Ticket ticket = tDao.selectOne(ticketId);

		if (ticket == null) {
			System.out.println("발권되지 않은 티켓정보입니다.");
		}
		System.out.println(ticket);
	}
	
	public int inputTicketId() {
	System.out.println("Ticket_id > ");
	return Integer.parseInt(sc.nextLine());
	}

	

	private void deleteTicket() {

	}
}
