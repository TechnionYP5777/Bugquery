package com.bugquery.serverside.examplesparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackTrace;
/**
 * 
 * @author rodedzats & ZivIzhar
 * @since 24.5.2017
 *
 */
public class ExamplesParser {
	private static final String exceptionTypesFile = "examples/ExceptionTypes.xml";
	private static final String postsPath = "examples/posts/";
	private Document getDocFromXmlFile(String xmlName) {
		ClassLoader classLoader = getClass().getClassLoader();		
		File fXmlFile = new File(classLoader.getResource(xmlName).getFile());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	public List<String> getExceptionTypes() {
		List<String> exceptionTypes = new ArrayList<>();
		Document doc = getDocFromXmlFile(exceptionTypesFile);
		if(doc == null)
			return exceptionTypes;
		NodeList nList = doc.getElementsByTagName("type");
		for (int i = 0; i < nList.getLength(); ++i)
			if (nList.item(i).getNodeType() == Node.ELEMENT_NODE)
				exceptionTypes.add(((Element) nList.item(i)).getAttribute("id"));
		return exceptionTypes;
	}
	
	public String getStackTraceByExceptionType(String exceptionType) {
		Document doc = getDocFromXmlFile(exceptionTypesFile);
		if (doc == null)
			throw new IllegalArgumentException();
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		String stackTrace = "";
		try {
			stackTrace = (String) xpath.compile("//type[@id=\"" + exceptionType + "\"]").evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return stackTrace;
	}
	
	public List<Post> getPosts(String exceptionType) {
		List<Post> posts = new ArrayList<>();
		Document doc = getDocFromXmlFile(postsPath + exceptionType + ".xml");
		if(doc == null)
			return posts;
		NodeList nList = doc.getElementsByTagName("post");
		for (int i = 0; i < nList.getLength(); ++i)
			if (nList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) nList.item(i);
				StackTrace s = new StackTrace(e.getAttribute("stacktrace"));
				Post p = new Post(s);
				p.setQuestion(e.getAttribute("question"));
				p.setAnswer(e.getAttribute("answer"));
				posts.add(p);
			}
		return posts;
	}
}
