package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCExample3 {
	
	public static void main(String[] args) {
	// * 부서별 최고 급여를 받는 직원의 이름, 직급, 부서, 급여를 부서 순으로 정렬하여 조회	

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String type = "jdbc:oracle:thin:@"; // JDBC 드라이버 종류
			String ip = "localhost"; // DB 서버 컴퓨터 IP
			String port = ":1521"; // port번호 // 1521 기본값
			String sid = ":XE"; // DB이름
			String user = "kh"; // 사용자 계정
			String pw = "kh1234"; // 비밀번호
			
			conn = DriverManager.getConnection(type+ip+port+sid, user, pw);
			
			String sql = "SELECT EMP_NAME, JOB_CODE, DEPT_CODE, SALARY FROM EMPLOYEE WHERE SALARY IN (SELECT MAX(SALARY) FROM EMPLOYEE GROUP BY DEPT_CODE)";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String jobCode = rs.getString("JOB_CODE");
				String deptCode = rs.getString("DEPT_CODE");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("이름 : %s/ 직급 : %s/ 부서 : %s/ 급여 : %d\n", empName, jobCode, deptCode, salary);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			
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
