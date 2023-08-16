package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Emp;

public class JDCBExample5 {
	
	public static void main(String[] args) {

		
		// 부서명을 입력받아 같은 부서에 있는 사원의
		// 사원명, 부서명, 급여 조회
		
		Scanner sc = new Scanner(System.in);
		
		// JDBC 객체 참조 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			System.err.print("부서명 입력 : ");
			String input = sc.nextLine();
			
			// JDBC 참조변수에 알맞은 객체 대입
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			String user = "kh";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(type+ip+port+sid, user, pw);

			String sql = "SELECT EMP_NAME, NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE, SALARY"
						+ " FROM EMPLOYEE"
						+ " LEFT JOIN DEPARTMENT ON(DEPT_ID = DEPT_CODE)"
						+ " WHERE NVL(DEPT_TITLE, '부서없음') = '" + input + "'";
			// Java에서 작성되는 SQL에 문자열 변수 추가할 경우
			// ''(DB 문자열 리터럴)이 누락되지 않도록 신경써야한다.
			// 만약 ''미작성 시 String 값은 컬럼명으로 인식되어
			// 부적합한 식별자 라는 오류가 발생함
			
			stmt = conn.createStatement(); // Statement 객체 생성
			
			// Statement 객체를 이용해서 SQL(SELECT)을 DB에 전달하여 실행한 후 ResultSet을 반환받아 rs에 저장
			rs = stmt.executeQuery(sql);
			
			
			// 조회결과(rs)를 List에 옮겨담기
			List<Emp> list = new ArrayList<>();
			
			while(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				int salary = rs.getInt("SALARY");
				
				// Emp 객체를 생성하여 컬럼값 담기
				Emp emp = new Emp(empName, deptTitle, salary);
				
				// 생성된 Emp객체를 List에 추가
				list.add(emp);
				
			}
			
			// 만약 List에 추가된 Emp객체가 없다면 "조회결과가 없습니다"
			// 있다면 순차적으로 출력
			
			
			if(list.isEmpty()) {
				
				System.out.println("조회결과가 없습니다.");
				
			}else {
				
				// 향상된 for문
				for(Emp emp : list) {
					System.out.println(emp);
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				
				if(rs != null)rs.close();
				if(stmt != null)stmt.close();
				if(conn != null)conn.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	
	}
	
}
