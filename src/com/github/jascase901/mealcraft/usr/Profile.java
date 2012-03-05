package com.github.jascase901.mealcraft.usr;

//import stuffs, also package name.
public class Profile{
    String name;
    String pantry;
	
    /**
       set the instance variable name to the user name and
       set pantry to "default"
    */
    public Profile(String name){
	this.setName(name);
	this.setPantry("default");
    }
    
    /**
       set the instance variable to the username and pantry
    */
    public Profile(String name, String pantry){
	this.setName(name);
	this.setPantry(pantry);
    }
    
    /**
       set the name of the profile
     */
    public void setName(String inputName){

	name = inputName;
    }

    /**
       set the pantry of the profile
    */
    public void setPantry(String inputPantry){

	pantry = inputPantry;
    }

    /**
       @return name
    */
    public String getName(){
	return name;
    }
    
    /**
       @return pantry
    */
    public String getPantry(){
	return pantry;
    }
    
}
		