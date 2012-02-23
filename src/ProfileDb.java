//package name
import java.sql.*;
//TODO:removeProfile,EditProfile for Iteration2

public class ProfileDb extends Database{

/**
    no-arg constructor, open a database connection and creates profile table
*/
    public ProfileDb() throws Exception{
        super();
          stat.executeUpdate("create table  if not exists  profile (profile_id  integer primary key , name unique, pantry);");

    }

    public void addProfile(Profile prf) throws Exception{
	PreparedStatement prep = conn.prepareStatement(
						       "insert into profile  values (?,?, ?);");

	//checks if profile is already in db
	if (getProfile(prf.getName())==null){
	    prep.setString(1,null);
	    prep.setString(2,""+prf.getName());
	    prep.setString(3,""+prf.getPantry());
	    prep.addBatch();

	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	}


    }
	
	/**
	    returns the name of the profile
		@param name of the profile
		@return name of the profile
	*/
	public String getName(String name) throws Exception{  //method seems unnecessary, maybe remove?
	    Profile profile = getProfile(name);
		return profile.getName();
	}
	
	/**
	    returns the pantry of the profile
		@param name of the profile
		@return name of the pantry the profile uses
	*/
	public String getPantry(String name) throws Exception{
	    Profile profile = getProfile(name);
		return profile.getPantry();
	}
	
	/**
        prints everything in the database
    */   
    public String printAll() throws Exception{
	ResultSet rs = stat.executeQuery("select * from profile;");
	String str="";
	while(rs.next()){

	    str+=rs.getString("name")+" ";
	    str+=rs.getString("pantry")+ "\n";
	}
	rs.close();
	return str;
    }
	
    /**
        get an ingredient object
    */
    public Profile getProfile(String name) throws Exception{
	    Profile profile=null;
		ResultSet rs = stat.executeQuery("select * from profile;");
		if(rs!=null){
		while(rs.next()){
		    if(name.equals(rs.getString("name"))){
			   profile = new Profile(rs.getString("name"),
			                         rs.getString("pantry"));
			}
		}
		    rs.close();
		}
		return profile;
    }

}
	    
