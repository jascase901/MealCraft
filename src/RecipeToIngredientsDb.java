import java.sql.*;

public class RecipeToIngredientsDb extends Database{

    public RecipeToIngredientsDb() throws Exception{
	super();
	stat.executeUpdate("create table  if not exists recipe-to-ingredients (recipe-id, ingredient-id, ingredient-quanity, units);"); 
    }
    public void addRelation(String recipe_name, String ingredient_name, 
				 double quantity, String units){


	
    }



}