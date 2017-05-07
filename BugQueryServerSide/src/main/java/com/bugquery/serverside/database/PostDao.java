package com.bugquery.serverside.database;

import java.util.List;

import com.bugquery.serverside.entities.Post;

public interface PostDao {
	List<Post> getAllPosts();
	Post getPost(Long id);
	void updatePost(Post p);
	void deletePost(Post p);
	List<Post> getAllPostsWithException(String ex);
}
