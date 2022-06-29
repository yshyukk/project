package timeTable;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class TableInfoManagement {

	Timestamp timestamp명 = new Timestamp(System.currentTimeMillis());

	Scanner sc = new Scanner(System.in);

	TimeTableDAO tableDao = TimeTableDAO.getInstance();

	public void TimeTableManagement() {
		while (true) {
			// 메뉴 출력
			menuPrint();
			// 메뉴 입력
			int menuNo = selectMenu();

			if (menuNo == 1) {
				// TIMETABLE 정보 입력
				insertInfo();
			} else if (menuNo == 2) {
				// TIMETABLE 등록된 정보 수정
				updateTableInfo();
			} else if (menuNo == 3) {
				// table에서 정보 삭제
				deleteTableInfo();
			} else if (menuNo == 9) {
				back();
				break;
			} else {
				showInputError();
			}
		}
	}

	private void menuPrint() {
		System.out.println("==============================================================");
		System.out.println("1. 열차시간표 정보입력 | 2. 열차시간표 정보수정 | 3. 열차정보 삭제 | 9. 뒤로가기");
		System.out.println("==============================================================");

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

	protected void back() {
		System.out.println("이전 메뉴로 돌아갑니다.");
	}

	public Date convertDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	protected void showInputError() {
		System.out.println("메뉴에서 입력해주시기 바랍니다.");
	}

	// time table에 열차정보 입력
	public void insertInfo() {
		TimeTable insertTable = new TimeTable();
		//SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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

		// dataformat을 입력 -> 내가 바꾸고자하는 날짜를 형식대로 입력->
		// SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println("yyyy-MM-dd a(p) HH:mm 형식으로 입력해주세요.");
		System.out.println("수정할 출발시간 > ");
		table.setDepartureTime(sc.nextLine());
		
		System.out.println("yyyy-MM-dd HH:mm 형식으로 입력해주세요.");
		System.out.println("수정할 도착시간 > ");
		table.setArriveTime(sc.nextLine());

		return table;

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
}
