import java.sql.*;
public class IngredientDb extends Database{
    
    /**
       no-arg constuctor, opens a database connection
    */
    public IngredientDb() throws Exception{
	super();
	 //stat.executeUpdate("drop table  if exists pantry;");
	 stat.executeUpdate("create table  if not exists  pantry (name primary key, calories, price, quantity);");
	        
    }
    /**
       returns the quantity of an item
       @param name of the item
       @return item quantity
    */
    public int getQuantity(String name) throws Exception{
	int quantity=-42;
	ResultSet rs = stat.executeQuery("select * from pantry;");
	if (rs!=null){
	while (rs.next()){
	    if (name.equals(rs.getString("name")))
		quantity= rs.getInt("quantity");
 
	}
	}
	rs.close();

	return quantity;
   }
    
    /**
       returns calories of item
       @param name of item
       @return calories of item
    */
    public int getCalories(String name) throws Exception{
	Ingredient ingredient = getIngredient(name);
	return ingredient.getCalories();

    }
  
    /**
       returns price of item
       @param name
       @return price of item as a double
    */
    public double getPrice(String name) throws Exception{
	
	Ingredient ingredient = getIngredient(name);
	return ingredient.getPrice();
    }

    /**
       returns name of itemx @TODO
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
    public void addIngredient(Ingredient ingredient, int quant) throws Exception{
	PreparedStatement prep = conn.prepareStatement(
						       "insert into pantry values ( ?,?,?,?);");
	
	//checks if ingredient is in db
	if (getIngredient(ingredient.getName())==null){
	  
	    prep.setString(1,""+ingredient.getName());
	    prep.setInt(2,ingredient.getCalories());
	    prep.setDouble(3,ingredient.getPrice());
	    prep.setInt(4,quant);
	    prep.addBatch();
	
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	}

	
    }
    public void addIngredient(Ingredient ingredient) throws Exception{
	addIngredient(ingredient, 1);
    }
    /**
       can be used in combination with get quanity to take a specified amount out of item
       i.e setQuantity(bacon, db.getQuantity("bacon")+100)
     */
    public void setQuantity(String name, int quantity) throws Exception{
	PreparedStatement prep = conn.prepareStatement(
				 "UPDATE pantry SET quantity = ? WHERE name = ?");
      
	
	prep.setInt(1, quantity);
	prep.setString(2, name);
	prep.executeUpdate();
    }
    /**
       @post prints everything in the database
    */   
    public String printAll() throws Exception{
	ResultSet rs = stat.executeQuery("select * from pantry;");
	String str="";
	while(rs.next()){
	    
	    str+=rs.getString("name")+" ";
	    str+=rs.getString("calories")+ " ";
	    str+=rs.getString("price")+ " ";
	    str+=rs.getString("quantity")+ "\n";
	}
	rs.close();
	return str;
    }
    
    private Ingredient getIngredient(String name) throws Exception{
	Ingredient ingredient=null;
	ResultSet rs = stat.executeQuery("select * from pantry;");
	if (rs!=null){
	while (rs.next()){
	    if (name.equals(rs.getString("name"))){
		ingredient = new Ingredient( rs.getString("name"), 
					     rs.getInt("calories"), 
					     rs.getDouble("price"));
	    }
	}
	    rs.close();

	}
	return ingredient;
    }



   
}
