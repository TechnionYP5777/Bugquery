package com.bugquery.serverside.examplesparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * 
 * @author rodedzats & ZivIzhar
 * @since 24.5.2017
 *
 */
public class ExamplesParser {
	private static final String exceptionTypesFile = "examples/ExceptionTypes.xml";
	public  List<String> getExceptionTypes() {
		List<String> exceptionTypes = new ArrayList<>();
		ClassLoader classLoader = getClass().getClassLoader();		
		File fXmlFile = new File(classLoader.getResource(exceptionTypesFile).getFile());
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
		NodeList nList = doc.getElementsByTagName("type");
		for (int i = 0; i < nList.getLength(); ++i)
			exceptionTypes.add(nList.item(i).getTextContent());
		return exceptionTypes;
	}
}
