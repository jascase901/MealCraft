//import sql stuff
public class Ingredient extends Item{
    String name;
    int calories;
   
    double price;
    /**
       sets every instance variable to a flag variable so we
       can know when the user decides not to input a catagory
    */
   
    public Ingredient(){
	this("", -42, -42.0);
    }
    /**
       sets every instance varaible but name to a flag var
       @param  name
    */
    public Ingredient(String name){
	this(name, -42, -42.0);
    }
    /**
       sets every instance variable but name and calories to varflag
       @param name
       @param calories
    */
    public Ingredient(String name, int calories){
	this(name, calories, -42);
    } 
    /**
       sets every instance variable but name and calories to varflag
       @param name
       @param price
    */
    public Ingredient(String name, double price){
	this(name, -42, price );
    } 


    /**
       Sets every instance variable
       @param name
       @param calories
       @param price
    */
    public Ingredient(String name, int calories, double price){
	this.name=name;
	this.calories=calories;
	this.price=price;

    }
    /**
       @return calories
    */
    public int getCalories(){
	return calories;
    }
   

    /**
       @return price
    */
    public double getPrice(){
	return price;
    }
    /**
       @return name;
    */
    public String getName(){
	return name;
    }

}