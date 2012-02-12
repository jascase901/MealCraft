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
	ingr.addIngredient(bacon);
	assertEquals(-42.0, ingr.getPrice("bacon"));
  }
    public void testAdd2() throws Exception {
	Ingredient bacon = new Ingredient("bacon", 54.0);
	IngredientDb ingr = new IngredientDb();
	ingr.addIngredient(bacon);
	assertEquals(54.0, ingr.getPrice("bacon"));
  }
    public void testAdd3() throws Exception {
	Ingredient bacon = new Ingredient("bacon", 54);
	IngredientDb ingr = new IngredientDb();
	ingr.addIngredient(bacon);
	assertEquals(54, ingr.getCalories("bacon"));
  }
    public void testAdd4() throws Exception {
	Ingredient bacon = new Ingredient("bacon", 54, 54.0);
	IngredientDb ingr = new IngredientDb();
	ingr.addIngredient(bacon);
	assertEquals(54, ingr.getCalories("bacon"));
	assertEquals(54.0, ingr.getPrice("bacon"));
      
  }
    public void testSetQuantity1() throws Exception{
	Ingredient bacon = new Ingredient("bacon", 54, 54.0);
	IngredientDb ingr = new IngredientDb();
	ingr.addIngredient(bacon);
	ingr.setQuantity("bacon", 2);
	assertEquals(2,ingr.getQuantity("bacon"));
    }


 public void testSetQuantity2() throws Exception{
	Ingredient bacon = new Ingredient("bacon", 54, 54.0);
	IngredientDb ingr = new IngredientDb();
	ingr.addIngredient(bacon);
	ingr.setQuantity("bacon", ingr.getQuantity("bacon")+1);
	assertEquals(2,ingr.getQuantity("bacon"));
    }


}