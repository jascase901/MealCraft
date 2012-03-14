package com.github.jascase901.mealcraft.db;
import java.sql.*;

import com.github.jascase901.mealcraft.system.Quantity;
import com.github.jascase901.mealcraft.system.Ingredient;
public class IngredientDb extends Database implements Indexable{

	/**
       no-arg constructor, opens a database connection creates a pantry table
	 */
	public IngredientDb() throws Exception{
		super();
		//stat.executeUpdate("drop table  if exists pantry;");
		
		stat.executeUpdate("create table  if not exists  pantry (ingredient_id  integer primary key , name unique, calories, price, amount, units);");

	}
	/**
       returns the quantity of an item
       @param name of the item
       @return item quantity
	 */
	public Quantity getQuantity(String name) throws Exception{
		Quantity quantity=null;
		ResultSet rs = stat.executeQuery("select * from pantry;");
		if (rs!=null){

			while (rs.next()){
				if (name.equals(rs.getString("name")))
					quantity = new Quantity(rs.getDouble("amount"), rs.getString("units"));

			}
		}
		rs.close();

		return quantity;
	}
	public int getId(String name) throws Exception{
		int id = -42;
		ResultSet rs = stat.executeQuery("select * from pantry;");
		if (rs!=null){
			while (rs.next()){
				if (name.equals(rs.getString("name")))
					id = rs.getInt("ingredient_id");

			}
		}
		rs.close();

		return id;

	}

	/**
       returns calories of item
       @param name of item
       @return calories of item
	 */
	public int getCalories(String name) throws Exception{
		Ingredient ingredient = getIngredient(name);
		return ingredient.getCalories();

	}

	/**
       returns price of item
       @param name
       @return price of item as a double
	 */
	public double getPrice(String name) throws Exception{

		Ingredient ingredient = getIngredient(name);
		return ingredient.getPrice();
	}

	/**
       returns name of itemx @TODO
       @param name of item
       @return the expiration date of item
	 */
	public int getExpiration(String name){
		return 42;

	}


	/**
       adds an ingredient to the database
       @param ingredient
	 */
	public void addIngredient(Ingredient ingredient, Quantity quant) throws Exception{
		PreparedStatement prep = conn.prepareStatement(
				"insert into pantry  values (?, ?, ?,?,?,?);");

		//checks if ingredient is in db
		if (getIngredient(ingredient.getName())==null){
			prep.setString(1,null);
			prep.setString(2,""+ingredient.getName());
			prep.setInt(3,ingredient.getCalories());
			prep.setDouble(4,ingredient.getPrice());
			prep.setDouble(5,quant.getAmount());
			prep.setString(6, quant.getUnits());
			prep.addBatch();

			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
		}


	}
	/**
       adds an ingredient to db
	 */
	public void addIngredient(Ingredient ingredient) throws Exception{
		Quantity quant = new Quantity(-42, "");
		addIngredient(ingredient,quant);
	}
	public void addIngredient(Ingredient ingredient, double amount, String units) throws Exception{
		Quantity quant = new Quantity(amount, units);
		addIngredient(ingredient,quant);

	}
	/**
       can be used in combination with get quantity to take a specified amount out of item
       i.e setQuantity(bacon, db.getQuantity("bacon")+100)
	 */
	public void setQuantity(String name, double amount, String units) throws Exception{
		PreparedStatement prepAmount = conn.prepareStatement(
				"UPDATE pantry SET amount = ?   WHERE name = ?");
		PreparedStatement prepUnits = conn.prepareStatement(
				"UPDATE pantry SET units = ?   WHERE name = ?");


		prepAmount.setDouble(1, amount);
		prepAmount.setString(2, name);
		prepAmount.executeUpdate();
		prepUnits.setString(1, units);
		prepUnits.setString(2, name);
		prepUnits.executeUpdate();




	}
	/**
       @post prints everything in the database
	 */   
	public String printAll() throws Exception{
		ResultSet rs = stat.executeQuery("select * from pantry;");
		String str="";
		while(rs.next()){

			str+=rs.getString("name")+" ";
			str+=rs.getString("calories")+ " ";
			str+=rs.getString("price")+ " ";
			str+=rs.getString("amount")+" ";
			str+=rs.getString("units")+"\n";
		}
		rs.close();
		return str;
	}
	/**
	 *returns last id
	 */
	public int getMaxId() throws Exception{
		int id;
		ResultSet rs = stat.executeQuery("SELECT MAX(ingredient_id) from pantry;");
		id = rs.getInt(1);
		rs.close();
		return id;
	}
	public int count() throws Exception{
		ResultSet rs = stat.executeQuery("select * from pantry;");
		int i =0;
		while(rs.next()){
			i++;

		}
		rs.close();
		return i;
	}
	/**
	 *  put the names of all ingredients in Database to a String Array
	 */
	public String[] namesArray() throws Exception{
		ResultSet rs = stat.executeQuery("select * from pantry;");
		String [] strArray= new String[300];
		int i=0;
		while(rs.next()){


			strArray[i]=rs.getString("name");
			i++;

		}
		return strArray;
	}

	/**
	 * put the calories of all ingredients in the Database to a String Array
	 */
	public String[] caloriesArray() throws Exception{
		ResultSet rs = stat.executeQuery("select * from pantry;");
		String [] strArray= new String[50];
		int i=0;
		while(rs.next()){


			strArray[i]=rs.getString("calories");
			i++;

		}
		return strArray;
	}

	/**
	 * put the amount of all ingredients in the Database to a String Array
	 */
	public String[] amountsArray() throws Exception{
		ResultSet rs = stat.executeQuery("select * from pantry;");
		String [] strArray= new String[50];
		int i=0;
		while(rs.next()){

			strArray[i]=rs.getString("amount");
			i++;

		}
		return strArray;
	}

	/**
	 * put the units of all ingredients in the Database to a String Array
	 */
	public String[] unitsArray() throws Exception{
		ResultSet rs = stat.executeQuery("select * from pantry;");
		String [] strArray= new String[50];
		int i=0;
		while(rs.next()){

			strArray[i]=rs.getString("units");
			i++;

		}
		return strArray;
	}

	/**
	 * put the prices of all ingredients in the Database to a String Array
	 */
	public String[] pricesArray() throws Exception{
		ResultSet rs = stat.executeQuery("select * from pantry;");
		String [] strArray= new String[50];
		int i=0;
		while(rs.next()){

			strArray[i]=rs.getString("price");
			i++;

		}
		return strArray;
	}
	public String[] catArray(String category) throws Exception{
		ResultSet rs = stat.executeQuery("select * from pantry;");
		String [] strArray= new String[3000];
		int i=0;
		while(rs.next()){

			strArray[i]=rs.getString(category);
			i++;

		}
		return strArray;
	}

	/**
      gets an ingredient object
	 */
	private Ingredient getIngredient(String name) throws Exception{
		Ingredient ingredient=null;
		ResultSet rs = stat.executeQuery("select * from pantry;");
		if (rs!=null){
			while (rs.next()){
				if (name.equals(rs.getString("name"))){
					ingredient = new Ingredient( rs.getString("name"), 
							rs.getInt("calories"), 
							rs.getDouble("price"));
				}
			}
			rs.close();

		}
		return ingredient;
	}




}
