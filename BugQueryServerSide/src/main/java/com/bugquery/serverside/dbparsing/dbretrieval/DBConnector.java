package com.bugquery.serverside.dbparsing.dbretrieval;

import java.sql.SQLException;
import java.util.List;

import com.bugquery.serverside.entities.Post;

/*
 * @author yonzarecki
 */
public interface DBConnector {
	List<Post> getAllQuestionsWithTheException(String s) throws InstantiationException, 
									IllegalAccessException, ClassNotFoundException, SQLException;
}
