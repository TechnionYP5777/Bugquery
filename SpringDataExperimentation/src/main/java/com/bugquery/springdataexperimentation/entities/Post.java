package com.bugquery.springdataexperimentation.entities;

<<<<<<< HEAD
<<<<<<< 1e70f72dd672f7bb87cb1dcdb8afde7e7219cf24

=======
>>>>>>> Wrote test for PostRepository, currently not running #102
=======
>>>>>>> d67083d9cc89197652f98a8033596039e85ca8bd
import java.util.Arrays;
import java.util.List;

import javax.persistence.ElementCollection;
<<<<<<< HEAD
<<<<<<< 1e70f72dd672f7bb87cb1dcdb8afde7e7219cf24

=======
>>>>>>> Wrote test for PostRepository, currently not running #102
=======
>>>>>>> d67083d9cc89197652f98a8033596039e85ca8bd
import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Post class.
 * 
 * @author Amit Ohayon
 * @since May 9, 2017
 */
@Entity
public class Post extends AbstractPersistable<Long> {

	private static final long serialVersionUID = -0x630A44B439FC66FAL;
	private String stackTrace;
	
	@ElementCollection
	private List<String> l;
	
	public Post() {
		this.setId(null);
		l = Arrays.asList("hi","shalom","kise","leyad","halon");
<<<<<<< HEAD
<<<<<<< 1e70f72dd672f7bb87cb1dcdb8afde7e7219cf24

=======
>>>>>>> Wrote test for PostRepository, currently not running #102
=======
>>>>>>> d67083d9cc89197652f98a8033596039e85ca8bd
	}
	
	public Post(String stackTrace) {
		this();
		this.stackTrace = stackTrace;
	}

	/**
	 * Creates a new post instance.
	 */
	public Post(Long id) {
		this.setId(id);
		
	}

	/**
	 * Returns the stack trace.
	 * 
	 * @return
	 */
	public String getStackTrace() {

		return stackTrace;
	}

	/**
	 * @param stackTrace
	 *            the stack trace to set
	 */
	public void setStackTracee(String stackTrace) {
		this.stackTrace = stackTrace;
	}

}
