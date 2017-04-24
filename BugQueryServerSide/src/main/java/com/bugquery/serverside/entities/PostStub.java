package com.bugquery.serverside.entities;

import com.bugquery.serverside.stacktrace.StackTraceRetrieverTest;

/** TODO move to test hierarchy and document, perhaps make inner class of {@link StackTraceRetrieverTest}--yg
 * @author zivizhar
 */
public class PostStub extends Post {
  public PostStub(StackTrace stackTrace) {
    super(stackTrace);
  }
  
  public PostStub(String stString) {
    super(stString);
  }
  
}
