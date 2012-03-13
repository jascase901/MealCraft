package com.github.jascase901.mealcraft.test;

import com.github.jascase901.mealcraft.db.IngredientDb;
import com.github.jascase901.mealcraft.db.RecipeBookDb;
import com.github.jascase901.mealcraft.db.RecipeToIngredientsDb;
import com.github.jascase901.mealcraft.system.Ingredient;
import com.github.jascase901.mealcraft.system.Recipe;

import junit.framework.*;
import java.util.ArrayList;

public class RecipeToIngredientDbTester extends TestCase{
	private IngredientDb ingr;
	private RecipeBookDb recipeDb;
	private RecipeToIngredientsDb rToIng;
	private ArrayList<String> a;
	protected void setUp() throws Exception{
		ingr = new IngredientDb(); 
		recipeDb = new RecipeBookDb();
		//adding ingredients

		Ingredient beer = new Ingredient("beer", 54, 54.0);;
		ingr.addIngredient(beer, 1, "liter");

		Ingredient bacon = new Ingredient("bacon", 7, 54.0);;
		ingr.addIngredient(bacon, 7, "lbs");

		Ingredient hotdog = new Ingredient("hotdog", 6, 54.0);
		ingr.addIngredient(hotdog, 6, "lbs");

		//adding recipes
		RecipeBookDb myRecipeBook = new RecipeBookDb();
		Recipe baconStew = new Recipe("bacon stew", "brown bacon", "not kosher");
		myRecipeBook.addRecipe(baconStew);

		Recipe bacon_hotdog =new Recipe("bacon hotdog", "cook hotdog, brown bacon, wrap hotdog in bacon", "totally kosher");
		myRecipeBook.addRecipe(bacon_hotdog);


		rToIng= new RecipeToIngredientsDb();
		
		rToIng.addRelation("bacon stew", "bacon", 7, "lbs");
		rToIng.addRelation("bacon stew", "beer", 8, "liters");
		rToIng.addRelation("bacon hotdog", "bacon", 2, "lbs");
		rToIng.addRelation("bacon hotdog", "hotdog", 2, "lbs");
		
	}

	protected void tearDown() throws Exception{
		// put common cleanup code in here
		ingr.close(); 


	}
	private String toString(String[] str){
		String strList ="";
		
		for (String s : str){
			strList += s+" ";
		}
		return strList;
	}



	public void testGetQuantity1() throws Exception {

		//	String row = rToIng.getRecipeId()+" ";
		//+rToIng.getIngredientId()+" "+rToIng.getIngredientQuantity" "+rToIng.getUnits()
		double quant = rToIng.getQuantity("bacon stew", "bacon");
		assertEquals(7.0,  quant);
		ingr.close();
	}
	public void testGetUnits1() throws Exception {
		String units = rToIng.getUnits("bacon stew", "bacon");
		assertEquals("lbs",  units);
		ingr.close();
	}
	public void testGetQuantity2() throws Exception{
		double quant = rToIng.getQuantity("bacon hotdog", "hotdog");
		assertEquals(2.0, quant);
	}
	public void testGetUnits2() throws Exception{
		String units = rToIng.getUnits("bacon hotdog","hotdog");
		assertEquals("lbs", units);

	}
	
	public void testGetQuantity3() throws Exception{
		double quant = rToIng.getQuantity("bacon stew", "beer");
		assertEquals(8.0, quant);
	}
	public void testGetUnits3() throws Exception{
		String units = rToIng.getUnits("bacon stew","beer");
		assertEquals("liters", units);
	}
	//Gets all ingredients of r a recipe
	public void testIngredientsThatRequire1() throws Exception{
		String[] ingredients = rToIng.getIngredientsThatRequire("bacon stew");
		String[] g = {"bacon", "beer"};
		
		assertEquals(toString(g), toString(ingredients));
	
	}
	
	public void testIngredientsThatRequire2() throws Exception{
		String [] ingredients = rToIng.getIngredientsThatRequire("hot dogs");
		String [] g = {"hotdog", "bun", "bacon"};
		
		assertEquals(toString(g), toString(ingredients));
	}
	
	public void testRecipesThatRequire() throws Exception{
		String [] ingredients = rToIng.getRecipesThatRequire("bacon");
		String [] g = {"hotdogs", "bacon stew"};
		
		assertEquals(toString(g), toString(ingredients));
		
	}



	
}