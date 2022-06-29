package ticket;

import java.util.List;
import java.util.Scanner;

import timeTable.TimeTable;
import timeTable.TimeTableDAO;

public class TicketManagement {

	Scanner sc = new Scanner(System.in);

	TicketDAO tDao = TicketDAO.getInstance();
	TimeTableDAO tableDao = TimeTableDAO.getInstance();

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
				// 티켓취소
				deleteTicket();
			} else if (menuNo == 3) {
				// 티켓조회
				oneTicketInfo();
			} else if (menuNo == 9) {
				back();
				break;
			}
		}
	}

	private void menuPrint() {
		System.out.println("=========================================");
		System.out.println("1. 예매 | 2. 예매취소 | 3.나의 티켓조회 | 9.종료");
		System.out.println("=========================================");

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

	// 티켓 예약하기
	public void reservationTicket() {

		// 출발지와 도착지를 입력하면 TIMETABLE 정보를 출력
		// MEBMER ID -> null값이 아니면 예약완료

		// 출발, 도착지 결정
		TimeTable table = new TimeTable();

		System.out.println("출발지 > ");
		table.setDepartureLocation(sc.nextLine());
		System.out.println("도착지 > ");
		table.setArriveLocation(sc.nextLine());
		System.out.println("출발시간 > ");
		table.setDepartureTime(sc.nextLine());

		List<TimeTable> locList = tableDao.searchLocationInfo(table);

		for (TimeTable locSearchtable : locList) {
			System.out.println(locSearchtable);
		}

		Ticket ticket = new Ticket();

		System.out.println("Member_Id > ");
		ticket.setMemberId(sc.nextLine());
		System.out.println("Timetable_id > ");
		ticket.setTimetableId(Integer.parseInt(sc.nextLine()));

		List<Ticket> list = tDao.remainSeat(ticket.getTimetableId());
		System.out.println(ticket.getTimetableId());
		if (list.size() == 0) {
			System.out.println("해당열차는 매진입니다.");
		}

		for (Ticket aticket : list) {
			System.out.println(aticket);
		}
		System.out.println("좌석번호 형식은 호실(A~F), 좌석번호(1~20)까지 입니다.");
		System.out.println("좌석번호 > ");
		ticket.setSeatNum(sc.nextLine());
		tDao.reservation(ticket);

	}

	public void seatStcok(int timetableId) {
		System.out.println("좌석번호 형식은 호실(A~F), 좌석번호(1~20)까지 입니다.");
		System.out.println("EX : A1, B20");
		int tableId = Integer.parseInt(sc.nextLine());

	}

	public void oneTicketInfo() {
		// memberId로 티켓조회하기
		System.out.println("ID를 입력해주세요.");
		System.out.print("ID > ");
		String memberId = sc.nextLine();
		List<Ticket> list = tDao.searchMyTicket(memberId);

		if (list.size() == 0) {
			System.out.println("발권되지 않은 티켓정보입니다.");
			return;
		}
		// 종료되지않았으면 있으니까
		for (Ticket ticket : list) {
			System.out.println(ticket);
		}

	}

	public String inputMemberId() {
		System.out.println("ID를 입력해주세요.");
		System.out.println("ID > ");
		return sc.nextLine();
	}

	public void deleteTicket() {
		System.out.println("티켓ID를 입력해주세요.");
		System.out.println("티켓 ID > ");
		int ticketId = Integer.parseInt(sc.nextLine());

		tDao.delete(ticketId);

	}
}
