import java.sql.*;
import java.util.ArrayList;

public class RecipeToIngredientsDb extends Database{
    IngredientDb ingr = new IngredientDb();
    RecipeBookDb rb = new RecipeBookDb();
    public RecipeToIngredientsDb() throws Exception{
	super();
	stat.executeUpdate("create table  if not exists recipe_to_ingredients (recipe_id, ingredient_id, ingredient_quantity, units);"); 
    }

    //pre, ingredient in ingredientdb, recipe in recipedb
    //post connects the databases together
    public void addRelation(String recipe_name, String ingredient_name, 
				 double quantity, String units) throws Exception{

	if  (getQuantity(recipe_name, ingredient_name) ==-42){
	int ingredient_id = ingr.getId(ingredient_name);
	int recipe_id =rb.getId(recipe_name); 
	PreparedStatement prep = conn.prepareStatement(
						       "insert into recipe_to_ingredients  values (?, ?, ?,?);");
	//checks if ingredient is in db
	if (true){
	    prep.setInt(1,recipe_id);
	    prep.setInt(2,ingredient_id);
	    prep.setDouble(3, quantity);
	    prep.setString(4,"lbs");
	    prep.addBatch();
	
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	    }

       
	}
	
    }

    //sql query that gets the quantity for how many ingredients are required for recipe
    public ArrayList<String> getRecipesThatRequire( String ingredient_name) throws Exception{
	int ingredient_id = ingr.getId(ingredient_name);

	String str="";
	ResultSet rs = stat.executeQuery("select r.name from recipe_to_ingredients n"                                                    +" LEFT JOIN recipebook r"
					 +"      ON r.recipe_id=n.recipe_id"
					 +" LEFT JOIN pantry i"
                                         +"      ON n.ingredient_id=i.ingredient_id;");
	ArrayList<String> rsArray = rsToArrayList(rs);
	rs.close();
	
	return rsArray;

    	
	



    }


    //returns the quantity of ingredient in recipe
    public double  getQuantity( String recipe_name,String ingredient_name) throws Exception{
	int recipe_id = rb.getId(recipe_name);
	double quantity = -42;//means there is nothing there
	ResultSet rs = stat.executeQuery("SELECT ingredient_quantity "   
					 +"FROM " 
					 +"recipe_to_ingredients "
					 +"WHERE "+recipe_id+ "=recipe_to_ingredients.recipe_id");
	if (rs.next())
	    quantity = Double.parseDouble(rs.getString(1));
	
	rs.close();
	
	return quantity;
    }



 public String  getUnits( String recipe_name,String ingredient_name) throws Exception{
	int recipe_id = rb.getId(recipe_name);
	String units = "";//means there is nothing there
	ResultSet rs = stat.executeQuery("SELECT units "   
					 +"FROM " 
					 +"recipe_to_ingredients "
					 +"WHERE "+recipe_id+ "=recipe_to_ingredients.recipe_id");
	if (rs.next())
	    units = rs.getString(1);
	//System.out.println(units);
	rs.close();
	
	return units;
    }
	//any class that uses this needs to import array list, only works for 1 column
    private ArrayList<String> rsToArrayList(ResultSet rs) throws Exception{
	ArrayList<String> str = new ArrayList<String>();
	while(rs.next()){
	str.add(rs.getString(1));

	}

	return str;
    }
	
}