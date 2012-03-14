package com.github.jascase901.mealcraft.db;
import java.sql.*;
public abstract class Database{
	public  int NOT_INPUTED_NUM = -456346364;
	public String NOT_INPUTED_STR="-456346364"; 
	protected Connection conn;
	protected Statement stat;
	public Database() throws Exception{

		Class.forName("org.sqlite.JDBC");
		conn =
				DriverManager.getConnection("jdbc:sqlite:MealCraft.db");
		stat = conn.createStatement();
	}
	public void removeKey(String cat, String dbname, String rm_condition) throws Exception{
		String sql = "Delete FROM "+ dbname+" WHERE "+cat+" = '"+rm_condition+"';";
		System.out.println(sql);
		//String sql ="Delete FROM pantry WHERE name IS NULL;";
		stat.executeUpdate(sql);

	}
	public void close() throws Exception{
		if (conn!=null && !conn.isClosed()){
			conn.close();
		stat.close();
	}
		else
			System.out.print("DATABASE ALREADY CLOSED");
	}
}