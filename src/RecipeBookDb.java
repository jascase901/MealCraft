import java.sql.*;
public class RecipeBookDb extends Database{
    /**
       no-arg constuctor, opens a database connection, creates a recipe book table
    */
    public RecipeBookDb() throws Exception{
	super();
	  stat.executeUpdate("create table  if not exists  recipebook (recipe_id  integer primary key , name unique, steps, notes);"); 
    } 


    public String getSteps(String name) throws Exception{
	Recipe recipe= getRecipe(name);
	return recipe.getSteps();
    }
    public String getNotes(String name) throws Exception{
	Recipe recipe= getRecipe(name);
	return recipe.getNotes();
    }
    
    public void addRecipe(Recipe recipe) throws Exception{
	PreparedStatement prep = conn.prepareStatement(
					       "insert into recipebook  values (?, ?,?,?);");
	//checks if recipe  is in db
	if (getRecipe(recipe.getName())==null){
	    prep.setString(1,null);
	    prep.setString(2,""+recipe.getName());
	    prep.setString(3,recipe.getSteps());
	    prep.setString(4,recipe.getNotes());
	    prep.addBatch();
	
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	}




    }
    private Recipe getRecipe(String name) throws Exception{
	Recipe recipe=null;
	ResultSet rs = stat.executeQuery("select * from recipebook;");
	if (rs!=null){
	while(rs.next()){
	    if (name.equals(rs.getString("name"))){
		recipe= new Recipe(rs.getString("name"),
				   rs.getString("steps"),
				   rs.getString("notes"));
	    }
	}
	    rs.close();
	}
	return recipe;
    }

}

