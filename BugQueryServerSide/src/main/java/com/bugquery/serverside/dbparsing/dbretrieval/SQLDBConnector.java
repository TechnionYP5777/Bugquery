package com.bugquery.serverside.dbparsing.dbretrieval;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bugquery.serverside.entities.MinSOPost;
import com.bugquery.serverside.entities.Post;

/*
 * @author yonzarecki
 */
public class SQLDBConnector implements DBConnector {

	@Override
	public List<Post> getAllQuestionsWithTheException(String s)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Post> $ = new ArrayList<>();

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:4488/bugquery?user=root&password=root")){
	  		for (ResultSet ¢ = connection.createStatement().executeQuery("SELECT * FROM bugquery_index2 WHERE Ex='"+s +"'"); 
	  				¢.next();
	  				)
	  			$.add(new MinSOPost(¢.getString("StackTrace"),¢.getString("Question")));
		}
		return $;
	}

}
