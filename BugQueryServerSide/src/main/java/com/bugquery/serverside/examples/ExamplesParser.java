package com.bugquery.serverside.examples;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
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
	public static final String postsPath = "examples/posts/";
	private Document getDocFromXmlFile(String xmlName) {
		URL xmlURL = getClass().getClassLoader().getResource(xmlName);
		if(xmlURL == null)
			return null;
		File fXmlFile = new File(xmlURL.getFile());
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
		XPath xpath = XPathFactory.newInstance().newXPath();
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
	
	public String getLocation(String exceptionType){
		URL xmlURL = getClass().getClassLoader().getResource(ExamplesParser.postsPath + exceptionType + ".xml");
		return xmlURL == null ? null : xmlURL.getFile();
	}
}
