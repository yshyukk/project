package timeTable;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class TimeTableManagement extends TimeTable {

	Scanner sc = new Scanner(System.in);

	TimeTableDAO tableDao = TimeTableDAO.getInstance();

	public TimeTableManagement() {
		while (true) {
			// 메뉴 출력
			menuPrint();
			// 메뉴 입력
			int menuNo = selectMenu();
			// 각 기능별 실행
			if (menuNo == 1) {
				// 제품등록
				insertInfo();

			} else if (menuNo == 2) {
				updateTableInfo();
			} else if (menuNo == 3) {
				searchTime();
			} else if (menuNo == 4) {
				searchLocation();
			} else {
				exit();
			}
		}
	}

	private void menuPrint() {
		System.out.println("================================================================================");
		System.out.println("1. 열차시간표 정보입력 | 2. 열차시간표 정보수정 | 3.열차조회(시간) | 4. 열차조회(출발-도착) | 9.종료");
		System.out.println("================================================================================");

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

	// time table에 열차정보 입력
	public void insertInfo() {
		TimeTable table = new TimeTable();
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");

		System.out.println("timetable_id");
		table.setTimeTableId(Integer.parseInt(sc.nextLine()));
		System.out.println("train_id");
		table.setTrainId(Integer.parseInt(sc.nextLine()));
		System.out.println("departure_time");
		// sc.nextLine[string타입 -> Date타입으로]
		try {

			java.util.Date date = dataFormat.parse(sc.nextLine());
			table.setDepartureTime(new java.sql.Date(date.getTime()));

		} catch (ParseException e) {
			e.printStackTrace();

			System.out.println("arrive_time");
		}

		try {

			java.util.Date date = dataFormat.parse(sc.nextLine());
			table.setArriveTime(new java.sql.Date(date.getTime()));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("departure_location");
		table.setDepartureLocation(sc.nextLine());
		System.out.println("arrive_location");
		table.setArriveLocation(sc.nextLine());
	}

	public void updateTableInfo() {
		// 열차시간표 정보수정 - 출발시간, 도착시간
		// 바꾸고자 하는 table_id찾기
		int tableId = inputTableId();

		// table 정보 불러옴
		TimeTable table = tableDao.selectOne(tableId);

		if (table == null) {
			System.out.println("등록된 열차정보가 없습니다.");
			return;
		}
		// 해당하는 table_id의 출발시간-도착시간을 수정
		table = UpdateTableInfo(table);

		// DB에 업데이트
		tableDao.update(table);

	}

	private TimeTable UpdateTableInfo(TimeTable table) {
		System.out.println("기존 열차정보 >  " + tableDao.selectOne(getTimeTableId()));
		System.out.println("<<열차정보 수정 [수정하지 않을경우  O를입력해주세요.]>>");

		int Ok = Integer.parseInt(sc.nextLine());

		if (Ok != 0) {

			SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("departure_time");
			// sc.nextLine[string타입 -> Date타입으로]
			try {

				java.util.Date date = dataFormat.parse(sc.nextLine());
				table.setDepartureTime(new java.sql.Date(date.getTime()));

			} catch (ParseException e) {
				e.printStackTrace();

				System.out.println("arrive_time");
			}

			try {

				java.util.Date date = dataFormat.parse(sc.nextLine());
				table.setArriveTime(new java.sql.Date(date.getTime()));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return table;
	}

	private int inputTableId() {
		System.out.println("Table_id > ");
		return Integer.parseInt(sc.nextLine());

	}

}
