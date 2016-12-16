package com.bugquery.serverside.dbparsing;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.bugquery.serverside.stacktrace.StackTraceExtractor;

public class DBGetter {
	public void getConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    for (ResultSet ¢ = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugquery?user=root&password=root")
				.createStatement().executeQuery("SELECT * FROM so_posts"); ¢.next();){
	    	List<String> res = StackTraceExtractor.extract(¢.getString("Body"));
//	    	System.out.println("PostId: "+¢.getString("Id"));
	    	if (!res.isEmpty())
	    		System.out.println("PostBody: " + res.get(0));
	    }
			
	    
	}
	
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		new DBGetter().getConnection();
	}
}
