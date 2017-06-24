package com.bugquery.serverside.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.repositories.PostRepository;

/**
 * 
 * @author amit
 * @since June 26, 2017
 */
@Service
public class StackOverflowService {

	@Autowired
	private PostRepository repo;

	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_ADDRESS = "localhost:4499";

	public static void updatePosts() {
		throw new UnsupportedOperationException("Updating is not yet implemented");
	}

	public void importAnswersFromStackOverflow() throws Exception {
		Class.forName(COM_MYSQL_JDBC_DRIVER).newInstance();
		try (Connection conn = DriverManager
				.getConnection("jdbc:mysql://" + DB_ADDRESS + "/bugquery?user=root&password=root")) {
			int c = (int) repo.count();
			for (Long i = 1L; i <= c; ++i) {
				Post p = repo.findOne(i);
				p.setAnswer(getAnswerFromAnswerId(conn, p.getAnswerId()));
				repo.save(p);
				System.out.println(i);
			}
		}
	}

	private static String getAnswerFromAnswerId(Connection conn, String answerId) {

		String answer = null;
		if ("0".equals(answerId))
			return null;
		try (ResultSet rs = conn.createStatement()
				.executeQuery("SELECT * FROM so_posts USE INDEX(Id) WHERE Id = " + answerId)) {
			for (ResultSet i_rs = rs; i_rs.next();)
				answer = i_rs.getString("Body");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answer;
	}
}
