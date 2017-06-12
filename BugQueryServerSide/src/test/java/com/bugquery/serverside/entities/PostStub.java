package com.bugquery.serverside.entities;

/**
 * @author zivizhar
 */
public class PostStub extends Post {
	private static final long serialVersionUID = -4442127556598745974L;

	public PostStub(StackTrace stackTrace) {
		super(stackTrace);
	}

	public PostStub(String stString) {
		super(stString);
	}

}
