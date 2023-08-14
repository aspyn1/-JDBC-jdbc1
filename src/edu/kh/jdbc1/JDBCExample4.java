package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCExample4 {
	
	public static void main(String[] args) {
		// * 77년생 여자 사원과 동일한 부서이면서 동일한 사수를 가지고 있는 사원을 조회하시오. 
		//		사번, 이름, 부서코드, 사수번호, 주민번호, 고용일 조회
			
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String type = "jdbc:oracle:thin:@"; 
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			String user = "kh";
			String pw = "kh1234"; 
			
			conn = DriverManager.getConnection(type+ip+port+sid, user, pw);
			
//			String sql = "SELECT EMP_ID, EMP_NAME, DEPT_CODE, MANAGER_ID, EMP_NO, HIRE_DATE "
//					+ "FROM EMPLOYEE "
//					+ "WHERE (DEPT_CODE, MANAGER_ID) = (SELECT DEPT_CODE, MANAGER_ID "
//					+ "FROM EMPLOYEE "
//					+ "WHERE SUBSTR(EMP_NO, 1, 2) = '77' "
//					+ "AND SUBSTR(EMP_NO, 8, 1) = '2') ";
			
			String sql = "SELECT EMP_ID, EMP_NAME, DEPT_CODE, MANAGER_ID, EMP_NO, HIRE_DATE FROM EMPLOYEE WHERE (DEPT_CODE, MANAGER_ID) = (SELECT DEPT_CODE, MANAGER_ID FROM EMPLOYEE WHERE SUBSTR(EMP_NO, 1, 2) = '77' AND SUBSTR(EMP_NO, 8, 1) = '2')";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_Name");
				String deptCode = rs.getString("DEPT_CODE");
				String managerId = rs.getString("MANAGER_ID");
				String empNo = rs.getString("EMP_NO");
				Date hireDate = rs.getDate("HIRE_DATE");
				
				System.out.printf("사번 : %s/ 이름 : %s/ 부서코드 : %s/ 관리자사번 : %s/ 주민번호 : %s/ 입사일 : %s\n",
									empId, empName, deptCode, managerId, empNo, hireDate);
				
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
