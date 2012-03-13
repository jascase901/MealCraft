package com.github.jascase901.mealcraft.test;

import com.github.jascase901.mealcraft.db.RecipeBookDb;
import com.github.jascase901.mealcraft.system.Recipe;

import junit.framework.*;
public class RecipeBookDbTester extends TestCase{
    RecipeBookDb myRecipeBook;
    Recipe baconStew;
    Recipe hotdog;

protected void setUp() throws Exception { 
  	// put common setup code in
	myRecipeBook = new RecipeBookDb();
	baconStew = new Recipe("bacon stew", "brown bacon", "not kosher");
	myRecipeBook.addRecipe(baconStew);
	hotdog =new Recipe("bacon hotdog", "cook hotdog, brown bacon, wrap hotdog in bacon", "totally kosher");
	myRecipeBook.addRecipe(hotdog);
	
 
  }
   
  protected void tearDown()  throws Exception{
  	// put common cleanup code in here
      myRecipeBook.close();
  }
    

    public void testAdd1() throws Exception {


	assertEquals("not kosher",myRecipeBook.getNotes("bacon stew"));


	

  }
    public void testAdd2() throws Exception {
	assertEquals("brown bacon", myRecipeBook.getSteps("bacon stew"));
	
  }
    public void testSetQuantity1() throws Exception{
	
    }



    public void testprintAll() throws Exception{
	
	
    }
}