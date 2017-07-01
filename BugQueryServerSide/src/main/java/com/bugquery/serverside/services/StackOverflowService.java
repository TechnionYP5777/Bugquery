package com.bugquery.serverside.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.repositories.PostRepository;
import com.bugquery.serverside.stacktrace.StackTraceExtractor;

/**
 * Specific services for stack overflow.
 * 
 * @author amit, tony
 * @since June 26, 2017
 */
@Service
public class StackOverflowService {

	private JdbcTemplate jdbcTemplate;
	private PostRepository repo;

	@Autowired
	public StackOverflowService(ApplicationArguments args, PostRepository repo, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.repo = repo;
		if (args.containsOption("updateDB"))
			updatePosts(args.getNonOptionArgs().get(0));
	}

	public void updatePosts(String xmlLocation){
		importStackOverflowDB(xmlLocation);
		addToPosts();
	}

	private void addToPosts() {
		jdbcTemplate.query(new StreamingStatementCreator("SELECT * FROM so_posts"), (rs, rowNum) -> 
		{
			List<Post> results = new ArrayList<>();
			if (isJavaPost(rs.getString("Tags"))) {
				String body = rs.getString("Body");
				for (StackTrace stackTrace : StackTraceExtractor.extract(body)) {
					Post p = new Post(stackTrace);
					int acceptedAnswerId = rs.getInt("AcceptedAnswerId");
					if (rs.wasNull()){
						p.setAnswer(null);
					} else{
						p.setAnswer(""+acceptedAnswerId);
					}
					p.setQuestion(body);
					p.setTitle(rs.getString("Title"));
					results.add(p);
				}
			}
			
			repo.save(results);
			return null;
		});
		for (Post p : repo.findAll()){
			if  (p.getAnswerId() != null){
				String answer = jdbcTemplate.queryForObject("SELECT Body FROM so_posts WHERE Id = ?", String.class, p.getAnswerId());
				p.setAnswer(answer);
				repo.save(p);
			}
		}
	}

	private static boolean isJavaPost(String tags) {
		return tags != null && tags.toLowerCase().contains("java");
	}

	private void importStackOverflowDB(String xmlLocation) {
		jdbcTemplate.execute("DROP TABLE IF EXISTS so_posts");
		jdbcTemplate.execute(
				"CREATE TABLE so_posts(Id int PRIMARY KEY, AcceptedAnswerId int, Body Text, Title Text, Tags varchar(500))");
		jdbcTemplate.execute(
				"LOAD XML INFILE \"" + xmlLocation + "\" INTO TABLE so_posts(Id, AcceptedAnswerId, Body, Title, Tags)");		
	}
	
	class StreamingStatementCreator implements PreparedStatementCreator {
	    private final String sql;

	    public StreamingStatementCreator(String sql) {
	        this.sql = sql;
	    }

	    @Override
	    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	        final PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	        statement.setFetchSize(Integer.MIN_VALUE);
	        return statement;
	    }
	}
}
