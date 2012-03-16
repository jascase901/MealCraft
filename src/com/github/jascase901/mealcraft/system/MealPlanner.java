package com.github.jascase901.mealcraft.system;

import java.util.HashMap;

import com.github.jascase901.mealcraft.db.IngredientDb;
import com.github.jascase901.mealcraft.db.RecipeBookDb;
import com.github.jascase901.mealcraft.db.RecipeToIngredientsDb;

public class MealPlanner
{
	HashMap<String, Quantity> pantryHash = new HashMap();
	HashMap<String, HashMap<String, Quantity>> recipesHash = new HashMap<String, HashMap<String, Quantity>>();
	HashMap toBuyHash = new HashMap();
	
	public void go(){
		generateRecipesHash();
		for (String each:recipesHash.keySet()){
			HashMap<String, Quantity> ingredient=recipesHash.get(each);
			for (String ing:ingredient.keySet()){
				System.out.println(each+":" + ing+ ":"+ingredient.get(ing).getAmount()+" "+ingredient.get(ing).getUnits());
			}
			
		}

		
	}

	private void generatePantryHash(){
		IngredientDb pantry;
		try {
			pantry = new IngredientDb();
			String[] ingredients = pantry.namesArray();
			for (String ingredient: ingredients){
				if(ingredient != null){
					pantryHash.put(ingredient, pantry.getQuantity(ingredient));
				}
			}
			pantry.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private void generateRecipesHash(){
		RecipeToIngredientsDb recipeQuantity;
		RecipeBookDb recipeBook;


		try {

			recipeBook = new RecipeBookDb();
			recipeQuantity = new RecipeToIngredientsDb();
			String[] recipes = recipeBook.catArray("name");
			for (String recipe: recipes){
				if(recipe != null){
					String[] ingredients = recipeQuantity.getIngredients(recipe);
					HashMap<String, Quantity> temp = new HashMap<String, Quantity>();


					for(String ingredient: ingredients){
						if (ingredients != null){
							String units = recipeQuantity.getUnits(recipe, ingredient);
							double quantity = recipeQuantity.getQuantity(recipe, ingredient);
							temp.put(ingredient, new Quantity(quantity, units));
							
						}
					}
					if (recipe!=null && temp!= null){
						
						recipesHash.put(recipe, temp);
					}

				}
			}
			recipeBook.close();
			recipeQuantity.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}




}