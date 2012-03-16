package com.github.jascase901.mealcraft.system;
import java.io.*;
public class ShoppingList
{
	 public static void exportToFile(String filepath, String text){

		try{
			// Create file 
			FileWriter fstream = new FileWriter(filepath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(text);
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}
	public static void exportToFile( String text){
		exportToFile("shoppinglist.txt", text);
	}
}