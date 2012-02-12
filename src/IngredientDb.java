import java.sql.*;
public class IngredientDb {
    private Connection conn;
    private PreparedStatement prep;
    /**
       no-arg constuctor, opens a database connection
    */
    public IngredientDb() throws Exception{
	 Class.forName("org.sqlite.JDBC");
	 Connection conn =
	   DriverManager.getConnection("jdbc:sqlite:Ingredient.db");
	 Statement stat = conn.createStatement();
	 stat.executeUpdate("drop table  if exists pantry;");
	 stat.executeUpdate("create table pantry (id, name, calories, price, quantity);");
	 PreparedStatement prep = conn.prepareStatement(
						  "insert into pantry values (?, ?,?,?,?);");
       
    }
    /**
       returns the quantity of an item
       @param name of the item
       @return item quantity
    */
    public int getQuantity(String name){
	return 42;
    }
    /**
       returns calories of item
       @param name of item
       @return calories of item
    */
    public int getCalories(String name){
	return 42;
    }
    /**
       returns the name of the id
      @param items unique identify
       @return name of item
    */
    public String getName(int id){
	return "bacon";
    }
    /**
       returns price of item
       @param name
       @return price of item as a double
    */
    public double getPrice(String name){
	return 42;
    }

    /**
       returns name of itemx
       @param name of item
       @return the expiration date of item
    */
    public int getExpiration(String name){
	return 42;

    }
    public void close() throws Exception{
	conn.close();
    }

    /**
       adds an ingredient to the database
       @param ingredient
    */
    public void addIngredient(Ingredient ingredient, int quantity) throws Exception{
	prep.setString(1,""+1);
	prep.setString(2,""+ingredient.getName());
	prep.setString(3,""+ingredient.getCalories());
	prep.setString(4,""+ingredient.getPrice());
	prep.setString(5,""+quantity);
	conn.setAutoCommit(false);
	prep.executeBatch();
	conn.setAutoCommit(true);
    }
    /**
       can be used in combination with get quanity to take a specified amount out of item
       i.e setQuantity(bacon, db.getQuantity("bacon")+100)
     */
    public void setQuantity(String name, int quantity){
    }



   
}
