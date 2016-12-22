package com.bugquery.serverside.entities;

/**
 * @author zivizhar
 */
public class PostStub extends Post {
  public StackTrace stackTrace;
  public PostStub(StackTrace stackTrace) {
    super(stackTrace);
  }
  
  public PostStub(String stString) {
    super(stString);
  }
  
}
