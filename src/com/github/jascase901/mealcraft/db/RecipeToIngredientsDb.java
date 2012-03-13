package com.github.jascase901.mealcraft.db;
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
	public String[] getRecipesThatRequire( String ingredient_name) throws Exception{
String[] str = new String[56];
		
		String sql= "select i.name from recipe_to_ingredients n"  +" LEFT JOIN recipebook r"
				+"      ON r.recipe_id=n.recipe_id"
				+" LEFT JOIN pantry i"
				+"      ON n.ingredient_id=i.ingredient_id;";
		
		ArrayList<String> rsArray = collectArrayListOfSql(ingredient_name, sql, rb);
		str=rsArray.toArray(str);
	

		return str;






	}
	//sql query that gets the quantity for how many ingredients are required for recipe
	public String[] getIngredientsThatRequire( String recipe_name) throws Exception{
		String[] str = new String[56];
		
		String sql= "select i.name from recipe_to_ingredients n"  +" LEFT JOIN recipebook r"
				+"      ON r.recipe_id=n.recipe_id"
				+" LEFT JOIN pantry i"
				+"      ON n.ingredient_id=i.ingredient_id;";
		
		ArrayList<String> rsArray = collectArrayListOfSql(recipe_name, sql, ingr);
		str=rsArray.toArray(str);
	

		return str;






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
/*
 * prints all the ingredients in a single recipe
 */
	public String[]  getIngredients( String recipe_name) throws Exception{
		int recipe_id = rb.getId(recipe_name);

		String[] str= new String[50];
		int i=0;
		ResultSet rs = stat.executeQuery("SELECT ingredient_id "   
				+"FROM " 
				+"recipe_to_ingredients "
				+"WHERE "+recipe_id+ "=recipe_to_ingredients.recipe_id");
		while(rs.next()){
			str[i]=rs.getString(1);
		}
		//System.out.println(units);
		rs.close();

		return str;
	}
	
	public String[] catArray(String category) throws Exception{
		ResultSet rs = stat.executeQuery("select * from recipe_to_ingredients;");
		String [] strArray= new String[50];
		int i=0;
		while(rs.next()){

			strArray[i]=rs.getString(category);
			i++;

		}
		return strArray;
	}
	/*
	 * @param name: the name of the recipe, or ingredient you wish to use as an index
	 * @param sql: the sql you wish to exectue
	 * @pre data base initialised 
	 */
	private ArrayList<String> collectArrayListOfSql(String name, String sql, Indexable db) throws Exception{
		int id =db.getId(name);
		ResultSet rs = stat.executeQuery(sql);
		ArrayList<String> rsArray = rsToArrayList(rs);
		rs.close();

		return rsArray;
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