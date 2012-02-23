import java.sql.*;

public class RecipeToIngredientsDb extends Database{

    public RecipeToIngredientsDb() throws Exception{
	super();
	stat.executeUpdate("create table  if not exists recipe-to-ingredients (recipe_id, ingredient_id, ingredient-quanity, units);"); 
    }
    public void addRelation(String recipe_name, String ingredient_name, 
				 double quantity, String units) throws Exception{

	IngredientDB ingr = new IngredientDb();
	int recipeId = ingr.getId(recipe_name);


	//checks if ingredient is in db
	if (true){
	    prep.setInt(1,);
	    prep.setInt(2,-42);
	    prep.setDouble(3, -42.0);
	    prep.setString(4,"");
	    prep.addBatch();
	
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
       

	
    }

	
}