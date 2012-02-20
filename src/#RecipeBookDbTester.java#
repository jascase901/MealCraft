import junit.framework.*;
public class RecipeBookDbTester extends TestCase{
    RecipeBookDb myRecipeBook;
    Recipe baconStew;

protected void setUp() throws Exception { 
  	// put common setup code in
	myRecipeBook = new RecipeBookDb();
	baconStew = new Recipe("bacon stew", "brown bacon", "not kosher");
	myRecipeBook.addRecipe(baconStew);
 
  }
   
  protected void tearDown()  throws Exception{
  	// put common cleanup code in here
      myRecipeBook.close();
  }
    

    public void testAdd1() throws Exception {


	assertEquals("not kosher",myRecipeBook.getNotes("bacon stew"));


	

  }
    public void testAdd2() throws Exception {
	assertEquals("brown bacon ", myRecipeBook.getSteps("bacon stew"));
	
  }
    public void testSetQuantity1() throws Exception{
	
    }



    public void testprintAll() throws Exception{
	
	
    }
}