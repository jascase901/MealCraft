import java.sql.*;

public class RecipeToIngredientsDb extends Database{
    IngredientDb ingr = new IngredientDb();
    RecipeBookDb rb = new RecipeBookDb();
    public RecipeToIngredientsDb() throws Exception{
	super();
	stat.executeUpdate("create table  if not exists recipe_to_ingredients (recipe_id, ingredient_id, ingredient_quanity, units);"); 
    }
    public void addRelation(String recipe_name, String ingredient_name, 
				 double quantity, String units) throws Exception{

	
	int ingredient_id = ingr.getId(ingredient_name);
	int recipe_id =rb.getId(recipe_name); 
	System.out.println(""+recipe_id);
	PreparedStatement prep = conn.prepareStatement(
						       "insert into recipe_to_ingredients  values (?, ?, ?,?);");
	//checks if ingredient is in db
	if (true){
	    prep.setInt(1,recipe_id);
	    prep.setInt(2,ingredient_id);
	    prep.setDouble(3, -42.0);
	    prep.setString(4,"lbs");
	    prep.addBatch();
	
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	    }
       

	
    }

	
}