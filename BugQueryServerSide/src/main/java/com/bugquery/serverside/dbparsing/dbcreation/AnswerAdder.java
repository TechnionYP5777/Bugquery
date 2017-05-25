package com.bugquery.serverside.dbparsing.dbcreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.repositories.PostRepository;
/**
 * TODO: change name of class to better one.
 * 
 * This class is responsible for updating the db with the answers of the questions.
 */
@Component
public class AnswerAdder {
	
	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	@Autowired
	private PostRepository repo;
	
	private static String address = "localhost:4499";
	Connection conn;
	public AnswerAdder(){
		try {
			Class.forName(COM_MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://"+address+"/bugquery?user=root&password=root");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void importAnswersFromStackOverflow()  {

		int c = (int) repo.count();
		for (int i = 1; i <= c; ++i){
			Post p = repo.findOne((long) i);
			String answer  = getAnswerFromAnswerId(p.getAnswerId());
			p.setAnswer(answer);
			repo.save(p);
			System.out.println(i);
		}

		
	}

	private String getAnswerFromAnswerId(String answerId) {
		
		
		String answer = null;
		if (answerId.equals("0"))
			return null;
		try(ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM so_posts USE INDEX(Id) WHERE Id = "+answerId)){
			for (ResultSet i_rs = rs; i_rs.next();) {
				answer =  i_rs.getString("Body");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return answer;
	}
}
