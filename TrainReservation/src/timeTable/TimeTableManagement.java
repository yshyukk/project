package timeTable;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import common.Management;
import ticket.Ticket;
import ticket.TicketDAO;

public class TimeTableManagement {

	Scanner sc = new Scanner(System.in);
	TimeTableDAO tableDao = TimeTableDAO.getInstance();
	TicketDAO tDao = TicketDAO.getInstance();

	public TimeTableManagement() {
		boolean role = new Management().selectRole();

		while (true) {
			// 메뉴 출력
			menuPrint(role);
			// 메뉴 입력
			int menuNo = selectMenu();

			if (menuNo == 1 && role) {
				// TIMETABLE 정보 입력
				insertInfo();
			} else if (menuNo == 2 && role) {
				// TIMETABLE 등록된 정보 수정
				updateTableInfo();
			} else if (menuNo == 3 && role) {
				// TIME TABLE 스케쥴 삭제
				deleteTableInfo();
			} else if (menuNo == 4) {
				// TICKET 만들기
				makeTicket();
			} else if (menuNo == 9) {
				back();
				break;
			}
		}
	}

	protected void menuPrint(boolean role) {
		System.out.println("==========================================================================");
		System.out.println("1. 열차시간표 정보입력 | 2. 열차시간표 정보수정 | 3. 열차정보 삭제 | 4. 티켓정보 입력 | 9.종료");
		System.out.println("==========================================================================");

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

	// time table에 열차정보 입력
	public void insertInfo() {
		TimeTable insertTable = new TimeTable();
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		System.out.println("train_id > ");
		insertTable.setTrainId(Integer.parseInt(sc.nextLine()));
		System.out.println("yyyy-MM-dd HH:mm 형식으로 입력해주세요.");
		System.out.println("departure_time > ");
		insertTable.setDepartureTime(sc.nextLine());

		System.out.println("yyyy-MM-dd HH:mm 형식으로 입력해주세요.");
		System.out.println("arrive_time > ");
		insertTable.setArriveTime(sc.nextLine());

		System.out.println("departure_location > ");
		insertTable.setDepartureLocation(sc.nextLine());
		System.out.println("arrive_location > ");
		insertTable.setArriveLocation(sc.nextLine());

		tableDao.insert(insertTable);
	}

	public void updateTableInfo() {
		// 열차시간표 정보수정 - 출발시간, 도착시간
		// 출발-목적지에 해당하는 TABLE 정보 수정 출력
		searchLocation();
		// 바꾸고자 하는 table_id찾기
		int tableId = inputTableId();
		// 새로 초기화된 vo에 찾은 table_id입력
		TimeTable currentTable = tableDao.selectOne(tableId);

		// table 정보 불러옴

		if (currentTable == null) {
			System.out.println("등록된 열차정보가 없습니다.");

		} else {
			// 해당하는 table_id의 출발시간-도착시간을 수정

			TimeTable updateTable = insertUpdate(currentTable);

			// DB에 업데이트
			tableDao.update(updateTable);

		}
	}

	// update할 시간찾기
	private TimeTable insertUpdate(TimeTable table) {
		// System.out.println("기존 열차정보 > " + tableDao.selectOne(getTimeTableId()));
		System.out.println("<<열차정보 수정 [수정하지 않을경우  O를입력해주세요.]>>");

		// dataformat을 입력 -> 내가 바꾸고자하는 날짜를 형식대로 입력->
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println("yyyy-MM-dd HH:mm 형식으로 입력해주세요.");
		System.out.println("departure_time");
		table.setDepartureTime(sc.nextLine());
		// sc.nextLine[string타입 -> Date타입으로]

		System.out.println("yyyy-MM-dd HH:mm 형식으로 입력해주세요.");
		System.out.println("arrive_time");
		table.setArriveTime(sc.nextLine());

		return table;

	}

	public Date convertDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	// tableId 찾기
	private int inputTableId() {
		System.out.println("Table_id > ");
		return Integer.parseInt(sc.nextLine());

	}

	private void deleteTableInfo() {
		// tableId찾고
		int tableId = inputTableId();
		TimeTable currentTable = tableDao.selectOne(tableId);
		// tableDao에 찾은 id값 넣기
		if (currentTable == null) {
			System.out.println("등록된 열차정보가 없습니다.");
		} else {
			tableDao.delete(currentTable);
		}

	}

	// timetable조회
	// 해당 출발지와 도착지 열차정보
	private void searchLocation() {
		// 출발지와 도착지 불러오기
		List<TimeTable> locList = tableDao.searchLocationInfo(inputLocation());

		for (TimeTable locSearchtable : locList) {
			System.out.println(locSearchtable);
		}

	}

	// 출발지 도착지 입력

	private TimeTable inputLocation() { // VO값 입력값으로 변경 TimeTable locTable = new
		TimeTable loctable = new TimeTable();
		System.out.println("departure_location");
		loctable.setDepartureLocation(sc.nextLine());
		System.out.println("arrive_location");
		loctable.setArriveLocation(sc.nextLine());

		return loctable;
	}

	// 출발시간입력하면 그 시간보다 후에 시간 조회하기
	// 원하는 출발시간 입력받고
	// DAO에 넘겨줌
	private void searchTime() {
		TimeTable ttable = new TimeTable();
		System.out.println("yyyy-MM-dd HH:mm 입력해주세요.");
		System.out.println("departure_time");
		ttable.setDepartureTime(sc.nextLine());
		tableDao.searchTimeInfo(ttable);

	}

	private void makeTicket() {
		// 출발지와 도착지를 입력하면 TIMETABLE 정보를 출력
		System.out.println("출발지 > ");
		TimeTable table = new TimeTable();
		table.setDepartureLocation(sc.nextLine());
		System.out.println("도착지 > ");
		table.setArriveLocation(sc.nextLine());

		List<TimeTable> locList = tableDao.searchLocationInfo(table);

		for (TimeTable locSearchtable : locList) {
			System.out.println(locSearchtable);
		}

		System.out.println("===================가격정보=====================");
		System.out.println("|			      동대구 -> 서울				  |");
		System.out.println("| KTX : 32,000 | ITX : 25,000 | 무궁화 : 10,000 |");
		System.out.println("==============================================");
		Ticket ticket = new Ticket();

		System.out.println("Timetable_id> ");
		ticket.setTimetableId(Integer.parseInt(sc.nextLine()));
		System.out.println("KTX - 1,3 | SRT - 2,4 | 무궁화 - 5");
		System.out.println("타는 곳 > ");
		ticket.setTrainSector(Integer.parseInt(sc.nextLine()));
		System.out.println("가격 > ");
		ticket.setPrice(Integer.parseInt(sc.nextLine()));

		tDao.insertTicketInfo(ticket);

	}

}
