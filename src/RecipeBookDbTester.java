import junit.framework.*;
public class RecipeBookDbTester extends TestCase{

protected void setUp() { 
  	// put common setup code in here
 
  }
   
  protected void tearDown() {
  	// put common cleanup code in here
 


  }
    

    public void testAdd1() throws Exception {

	RecipeBookDb myRecipeBook = new RecipeBookDb();
	Recipe baconStew = new Recipe("bacon stew", "brown bacon ", "not kosher");
	myRecipeBook.addRecipe(baconStew);
	assertEquals(myRecipeBook.getNotes("bacon stew"), "not kosher");


	

  }
    public void testAdd2() throws Exception {	
  }
    public void testAdd3() throws Exception {

  }
    public void testAdd4() throws Exception {

      
  }
    public void testSetQuantity1() throws Exception{
	
    }



    public void testprintAll() throws Exception{
	
	
    }
}