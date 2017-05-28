package com.bugquery.serverside.examplesparser;

import java.util.List;

public class ExamplesXMLCreator {
	private static String filesLocation = "examples/posts/";
	
	public static void createExamplesXML(){
		List<String> ExTypes = new ExamplesParser().getExceptionTypes();
		for (String exceptionType : ExTypes){
			ExamplesXMLCreator.createXML(exceptionType);
		}
	}
	
	private static void createXML(String ExceptionType){
		//TODO: implement, will do in a bit
	}
}
