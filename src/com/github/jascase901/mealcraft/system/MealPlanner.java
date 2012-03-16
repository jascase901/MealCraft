package com.github.jascase901.mealcraft.system;

import com.github.jascase901.mealcraft.db.RecipeBookDb;

public class MealPlanner
{
	public void go(){

		try {
			RecipeBookDb recipebook= new RecipeBookDb();
			String[] recipes = recipebook.catArray("name");
			for (String recipe:recipes){
				System.out.println(recipe);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}