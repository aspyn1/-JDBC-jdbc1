package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDCBExample1 {
	
	public static void main(String[] args) {
	
		// JDBC : Java에서 DB에 연결할 수 있게 해주는 Java Programming API(Java에 기본으로 내장된 인터페이스)
		// java.sql 패키지에서 제공
		
		// * JDBC를 이용한 어플리케이션을 만들 때 필요한 것 
		// 1. Java의 JDBC관련 인터페이스
		// 2. DBMS(Oracle)
		// 3. Oracle에서 Java 어플리케이션과 연결할 때 사용할
		//  	JDBC를 상속받아 구현한 클래스 모음(ojdbc. jar 라이브러리)
		// 		-> OracaleDrivd.class 이용 (JDBC 드라이버)
		
		
		// 1단계 : JDCB 객체 참조 변수 선언 (java.sql패키지의 인터페이스)
		
		Connection conn = null;
		// DB연결정보를 담은 객체 
		// -> DBMS 타입, 이름, IP, Port, 계정명, 비밀번호 저장
		// -> DBeaver의 계정 접속 방법과 유사함
		// * Java와 DB 사이를 연결해주는 통로
		
		Statement stmt = null;
		// Connection을 통해 SQL문을 DB에 전달하여 실행하고
		// 생성된 결과(ResultSet, 성공한 행의 개수)를 Java로 반환하는데 사용되는 객체
		
		ResultSet rs = null;
		// SELECT 질의 성공 시 반환되는데
		// 조회 결과 집합을 나타내는 객체
		
		
		try {
			// 2단계 : 참조변수에 알맞은 객체 대입
			
			// 1. DB연결에 필요한 Oracle JDBC Driver 메모리에 로드하기
			// 런타임 시점에 해당 경로의 클래스를 동적으로 로드함
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// -> ()안에 작성된 클래스의 객체를 반환
			// -> 메모리에 객체가 생성되어지고 JDBC필요 시 알아서 참조하여 사용
			// -> 생략해도 자동으로 메모리 로드가 진행됨(명시적으로 작성하는걸 권장)
			
			// 2. 연결정보를 담은 Connection을 생성
			// -> DriverManager 객체를 이용해서 Connection 객체를 만들어 얻어옴
			
			String type = "jdbc:oracle:thin:@"; // JDBC 드라이버 종류
			String ip = "localhost"; // DB 서버 컴퓨터 IP
			// == 127.0.0.1 (loop back ip) 
			String port = ":1521"; // port번호 // 1521 기본값
			String sid = ":XE"; // DB이름
			String user = "kh"; // 사용자 계정
			String pw = "kh1234"; // 비밀번호
			
			// DriverManager : 
			// 메모리에 로드된 JDBC 드라이버를 이용해서
			// Connection 객체를 만드는 역할
			
			conn = DriverManager.getConnection(type+ip+port+sid, user, pw);
			// url == type+ip+prot+sid == jdbc:oracle:thin:@localhost:1521:XE
			
			
			// 중간확인
			// System.out.println(conn);
			
			// 3. SQL 작성
			// ** JAVA 에서 작성되는 SQL은 구문 마지막에 ;을 작성하면 안됨
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY, HIRE_DATE FROM EMPLOYEE";
			
			// 4. Statment 객체 생성
			// -> Connection 객체를 통해서 생성
			stmt = conn.createStatement();
			
			
			// 5. 생성된 Statement 객체에
			// sql을 적재하여 실행한 후 
			// 결과를 반환받아와서 rs 변수에 저장
			rs = stmt.executeQuery(sql);
			// executeQuery() : SELECT문 수행 메서드, ResultSet 반환
			
			
			
			// 3단계 : SQL을 수행해서 반환받은 결과(ResultSet)을
			// 			한 행씩 접근해서 컬럼값 얻어오기
			
			while(rs.next()) {
				// rs.next() : rs가 참조하고있는 ResultSet 객체의
				//				첫 번째 컬럼부터 순서대로 한 행씩 이동하며
				//				다음 행이 있을 경우 true, 없으면 false반환
			
				// rs.get자료형("컬럼명")
				String empId = rs.getString("EMP_ID");
				// 현재 행의 "EMP_ID" 문자열 컬럼의 값을 얻어옴
				int salary = rs.getInt("SALARY");
				String empName = rs.getString("EMP_NAME");
				Date hireDate = rs.getDate("HIRE_DATE"); // java.sql.Date
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d / 입사일 : %s\n", 
						empId, empName, salary, hireDate.toString());
			
				// java.sql.Date 의 toSting()은 yyyy-mm-dd 형식으로 오버라이딩 되어있음.
				
			}
			
		}catch(ClassNotFoundException e) {
			System.err.println("JDBC 드라이버 경로가 잘못 작성되었습니다.");
		}catch(SQLException e) {
			// SQLException : DB 관련 최상위 예외
			e.printStackTrace();
		}finally{
			
			// 4단계 : 사용한 JDBC 객체 자원 반환( close() )
			// ResultSet, Statement, Connection (생성 역순으로 닫는 것을 권장)
			
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn!= null) conn.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	

}
