import java.sql.*;
public abstract class Database{
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