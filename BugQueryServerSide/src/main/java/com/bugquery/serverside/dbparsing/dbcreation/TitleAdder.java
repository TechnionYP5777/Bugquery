package com.bugquery.serverside.dbparsing.dbcreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.repositories.PostRepository;

@Component
public class TitleAdder {
private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	@Autowired
	private PostRepository repo;
	
	private static String address = "localhost:3306";
	Connection conn;
	public TitleAdder(){
		try {
			Class.forName(COM_MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://"+address+"/bugquery?user=root&password=root!");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void importTitlesFromBugqueryIndex(){
		int c = (int) repo.count();
		for (int i = 1; i <= c; ++i){
			Post p = repo.findOne((long) i);
			int postId = (int) (p.getId() - 1);
			p.setTitle(getTitleFromPostId(postId));
			repo.save(p);
			System.out.println(i);
		}
	}
	
private String getTitleFromPostId(int postId) {
		
		
		String title = null;
		try(ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM bugquery_index  WHERE BugQueryId = "+postId)){
			for (ResultSet i_rs = rs; i_rs.next();) {
				title =  i_rs.getString("Title");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return title;
	}
}
