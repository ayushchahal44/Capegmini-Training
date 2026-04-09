package com.capg.jdbc;
import java.sql.*;
public class PreparedStatementEx1 
{ 	public static void main(String[] args)throws Exception  {
		System.out.println("Oracle Connect Example.");
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String userName = "capgdb"; 
		String password = "capgdb";	 
		  Class.forName(driver);
			conn = DriverManager.getConnection(url,userName,password);		
			
			String query="insert into Employeeinfo2 values(?,?,?)";			
			//Step1 :  Get PreparedStatement 
			PreparedStatement pstmt=conn.prepareStatement(query);
			//Step2 :  Set parameters
			pstmt.setInt(1,555);
			pstmt.setString(2,"Babu");
			pstmt.setDouble(3,9999.66);
			//Step3 : execute the query
			int i=pstmt.executeUpdate();
			
			System.out.println("Record inserted count :"+i);
		   	//Execute query once again
			
			pstmt.setInt(1,222);
			pstmt.setString(2,"Bhanu");
			pstmt.setDouble(3,5450.66);
			i=pstmt.executeUpdate(); 			
	System.out.println("Query executed for the second time count :"+i);				
	}}  