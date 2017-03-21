package com.bugquery.serverside.entities;

import static org.assertj.core.api.Assertions.*;

import java.net.URLEncoder;

import org.junit.Test;

/**
 * 
 * @author Amit
 * @since Jan 19, 2017
 *
 */
public class StackSearchTest {

	@Test
	@SuppressWarnings("static-method")
	public void shouldDecodeTrace() throws Exception {
		assertThat((new StackSearch(URLEncoder.encode("decoded trace", "UTF-8"))).getTrace())
				.isEqualTo("decoded trace");
	}
}
