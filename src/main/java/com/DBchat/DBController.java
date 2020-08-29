package com.DBchat;

//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bean.ChatRoom;
import com.bean.History;

@Component("DBController")
public class DBController {
	
	 @Value("${spring.datasource.driverClassName}")
	 private String driver;
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/CHAT";
//	static final String DB_URL = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12348159";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "rajan";
//	static final String USER = "sql12348159";
//	static final String PASS = "dEEwGHqICT";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);
			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
//			Student student = new Student();
//			student.setId("123");
//			student.setFirst("S");
//			student.setLast("Nayak");
//			student.setInstitution("2");
//			student.setCourse("3");
//			student.setYear("IV");
//			student.setBranch("2");
//			
//			ArrayList<Student> arrayList = new ArrayList<Student>();

			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id, first, last, age FROM Employees";
			ResultSet rs = stmt.executeQuery(sql);

			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				int id  = rs.getInt("id");
				int age = rs.getInt("age");
				String first = rs.getString("first");
				String last = rs.getString("last");

				//Display values
				System.out.print("ID: " + id);
				System.out.print(", Age: " + age);
				System.out.print(", First: " + first);
				System.out.println(", Last: " + last);
			}
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		System.out.println("Goodbye!");
	}//end main

	public ArrayList<History> fetch(String chatRoom, String time) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<History> arrayList = new ArrayList<History>();
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM History WHERE chatRoom = '" + chatRoom + "'";
			
			if(!StringUtils.isEmpty(time)) {
				sql += " AND ";
				sql += "time > '" + time + "'";
			}
			
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				History history = new History();
				history.setUser(rs.getString("user"));
				history.setMsg(rs.getString("msg"));
				history.setTime(rs.getString("time"));
				arrayList.add(history);
			}
			stmt.close();
			conn.close();

		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return arrayList;
	}
	
	public boolean addChat(String chatRoom, String user, String msg , String time) {
		Connection conn = null;
		Statement stmt = null;
		boolean isSuccess = true;
		ArrayList<History> arrayList = new ArrayList<History>();
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			String sql;
			sql = "INSERT INTO History (chatRoom, user, msg, time) VALUES (";
			if(!StringUtils.isEmpty(chatRoom) && !StringUtils.isEmpty(user) && !StringUtils.isEmpty(msg)) {
				sql += chatRoom+",";
				sql += "'" +user+"',";
				sql += "'" +msg+"',";
				sql += "'" +time+"')";
				int a = stmt.executeUpdate(sql);
			}
			stmt.close();
			conn.close();

		}catch(SQLException se){
			isSuccess = false;
			se.printStackTrace();
		}catch(Exception e){
			isSuccess = false;
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return isSuccess;
	}
	
	public boolean createRoom(String password , String time) {
		Connection conn = null;
		Statement stmt = null;
		boolean isSuccess = true;
		ArrayList<History> arrayList = new ArrayList<History>();
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			String sql;
			sql = "INSERT INTO chatRoom (password, time) VALUES (";
			if(!StringUtils.isEmpty(password) && !StringUtils.isEmpty(time)) {
				sql += password+",";
				sql += "'" +time+"')";
				int a = stmt.executeUpdate(sql);
			}
			stmt.close();
			conn.close();

		}catch(SQLException se){
			isSuccess = false;
			se.printStackTrace();
		}catch(Exception e){
			isSuccess = false;
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return isSuccess;
	}
	
	public ArrayList<ChatRoom> getChatRoomId(String password, String time) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<ChatRoom> arrayList = new ArrayList<ChatRoom>();
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM chatRoom WHERE password = '" + password + "' AND time = '" + time + "'";;
			
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				ChatRoom chatRoom = new ChatRoom();
				chatRoom.setId(rs.getString("id"));
				chatRoom.setPassword(rs.getString("password"));
				arrayList.add(chatRoom);
			}
			stmt.close();
			conn.close();

		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return arrayList;
	}
	
	public ArrayList<ChatRoom> validatePassword( String id ,String password) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<ChatRoom> arrayList = new ArrayList<ChatRoom>();
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM chatRoom WHERE id = '" + id + "' AND password = '" + password + "'";;
			
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				ChatRoom chatRoom = new ChatRoom();
				chatRoom.setId(rs.getString("id"));
				chatRoom.setPassword(rs.getString("password"));
				arrayList.add(chatRoom);
			}
			stmt.close();
			conn.close();

		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return arrayList;
	}
	
}