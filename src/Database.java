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
    public void close() throws Exception{
	conn.close();
    }
}