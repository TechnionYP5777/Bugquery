package com.bugquery.serverside.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bugquery.serverside.entities.Post;

public class PostDaoImpl implements PostDao {
	static final String TABLE = "bugquery_index";
	
	JdbcTemplate jdbc;
	
	@Autowired
	public PostDaoImpl(JdbcTemplate jdbc) {
		super();
		this.jdbc = jdbc;
	}
	
	@Override
	public List<Post> getAllPosts() {
		return jdbc.query("SELECT * FROM " + TABLE,
				(rs,rowNum) -> new Post(
						Long.valueOf(rs.getLong("Id")), 
						rs.getString("StackTrace")));
	}

	@Override
	public Post getPost(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePost(Post p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePost(Post p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Post> getAllPostsWithException(String ex) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
