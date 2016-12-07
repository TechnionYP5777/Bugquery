package com.bugquery.serverside.dbparsing;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBGetter {
	public void getConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    for (ResultSet ¢ = DriverManager.getConnection("jdbc:mysql://localhost/stackoverflow?user=root&password=1234")
				.createStatement().executeQuery("SELECT * FROM posts"); ¢.next();)
			System.out.println(¢.getString("Id"));
	    
	}
	
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		new DBGetter().getConnection();
	}
}
