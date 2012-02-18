import junit.framework.*;
public class IngredientDbTester extends TestCase{

protected void setUp() { 
  	// put common setup code in here
 
  }
   
  protected void tearDown() {
  	// put common cleanup code in here
 


  }
    

    public void testAdd1() throws Exception {
	Ingredient bacon = new Ingredient("bacon");
	IngredientDb ingr = new IngredientDb();
	ingr.addIngredient(bacon, 1);
	assertEquals(-42.0, ingr.getPrice("bacon"));
	ingr.close();
  }
    public void testAdd2() throws Exception {
	Ingredient bananna = new Ingredient("bananna", 54.0);
	IngredientDb ingr = new IngredientDb();
	ingr.addIngredient(bananna);
	assertEquals(54.0, ingr.getPrice("bananna"));
	ingr.close();
  }
    public void testAdd3() throws Exception {
	Ingredient pork = new Ingredient("pork", 54);
	IngredientDb ingr = new IngredientDb();
	ingr.addIngredient(pork, 32);

	assertEquals(54, ingr.getCalories("pork"));
	ingr.close();
  }
    public void testAdd4() throws Exception {
	Ingredient beer = new Ingredient("beer", 54, 54.0);
	IngredientDb ingr = new IngredientDb();
	ingr.addIngredient(beer, 1);
	assertEquals(54.0, ingr.getPrice("beer"));
	ingr.close();
      
  }
    public void testSetQuantity1() throws Exception{
	Ingredient bacon = new Ingredient("bacon", 54, 54.0);
	IngredientDb ingr = new IngredientDb();
	ingr.setQuantity("bacon", 2);
	assertEquals(2,ingr.getQuantity("bacon"));
	ingr.close();
    }



    public void testprintAll() throws Exception{
	IngredientDb ingr = new IngredientDb();
	assertEquals("1 bacon 54 54.0 1\n1 potatoes 20 20.0 1\n",ingr.printAll());
	
    }
}