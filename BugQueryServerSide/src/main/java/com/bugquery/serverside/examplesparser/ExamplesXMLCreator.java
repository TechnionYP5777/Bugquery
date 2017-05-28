package com.bugquery.serverside.examplesparser;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.bugquery.serverside.Application;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.exceptions.GeneralDBException;
import com.bugquery.serverside.exceptions.InternalServerException;
import com.bugquery.serverside.exceptions.InvalidStackTraceException;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;


@Component
public class ExamplesXMLCreator {	
	
	
	private static StackTraceRetriever retriever;
	private static boolean run = false;
	
	public static void activate(){
		run = true;
	}
	
	@Autowired
	public void createExamplesXML(StackTraceRetriever ret){	
		if(run){
			retriever = ret;
			List<String> exTypes = new ExamplesParser().getExceptionTypes();
			for (String exceptionType : exTypes){
				createXML(exceptionType);
			}
		}
	}
	
	private void createXML(String exceptionType){
		String stackTrace = new ExamplesParser().getStackTraceByExceptionType(exceptionType);
		List<Post> $;
		try {
			$ = getResults(stackTrace);
		} catch (GeneralDBException | InvalidStackTraceException ¢) {
			throw new InternalServerException(¢);
		}
		createAndSaveXMLFile(exceptionType,$);
	}
	
	private void createAndSaveXMLFile(String exceptionType, List<Post> $) {
		  try {

				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// root element
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("posts");
				doc.appendChild(rootElement);
				
				for (Post p : $){
					// post element
					Element post = doc.createElement("post");
					rootElement.appendChild(post);
					
					// set stacktrace
					Attr stacktrace = doc.createAttribute("stacktrace");
					stacktrace.setValue(p.getStackTrace().getContent());
					post.setAttributeNode(stacktrace);
					
					// set question
					Attr question = doc.createAttribute("question");
					question.setValue(p.getQuestion());
					post.setAttributeNode(question);
					
					// set answer
					Attr answer = doc.createAttribute("answer");
					answer.setValue(p.getAnswerId());
					post.setAttributeNode(answer);
				}
				
				// write the content into xml file
				LoggerFactory.getLogger(Application.class).info(exceptionType);
				String filePath = new ExamplesParser().getLocation(exceptionType);
				LoggerFactory.getLogger(Application.class).info(filePath);
				File fXmlFile = new File(filePath);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(fXmlFile);
				transformer.transform(source, result);

		  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		
	}

	public List<Post> getResults(String trace) throws GeneralDBException, InvalidStackTraceException {
		return retriever.getMostRelevantPosts(trace, 10);
	}
}
