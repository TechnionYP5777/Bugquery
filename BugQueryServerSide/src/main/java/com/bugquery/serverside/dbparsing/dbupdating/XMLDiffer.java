package com.bugquery.serverside.dbparsing.dbupdating;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*This class takes two versions of a forum's xmls - a new one and
 *        an old one and creates a new xml file which contains the needed
 *        changes in the database in order to update the db with the new posts.
 *        ASSUMPTIONS :
 *         1. The posts' content don't change 
 *         2. Newer posts get higher id
 * @author ZivIzhar
 * @since 9.1.17 T
 */
public class XMLDiffer {
	private String oldXMLAddr, newXMLAddr, outputXMLAddr;

	/**
	 * @param oldXMLAddr
	 *            - address of the old xml file.
	 * @param newXMLAddr
	 *            - address of the new xml file.
	 * @param outputXMLAddr
	 *            - where to create the new xml file.
	 */
	public XMLDiffer(String oldXMLAddr, String newXMLAddr, String outputXMLAddr) {
		this.oldXMLAddr = oldXMLAddr;
		this.newXMLAddr = newXMLAddr;
		this.outputXMLAddr = outputXMLAddr;
	}
	
	/**
	 * 
	 * @param byID - true for diff according to the id's, false for according to dates.
	 * @return
	 */
	public Void createDiff(boolean byID){
		if(byID)
			createDiffID();
		else
			createDiffDate();
		return null;
	}

	private Void createDiffID() {
		Set<Integer> oldSet = getAllIDs(this.oldXMLAddr), diffSet = getAllIDs(this.newXMLAddr);
		// diffSet = newSet - all the Id's < max(oldset)
		for (int maxId = Collections.max(new ArrayList<>(oldSet)).intValue(), ¢ = 0; ¢ <= maxId; ++¢)
			diffSet.remove(Integer.valueOf(¢));

		// Go over the new XML file and put the new rows in the output file.
		try (BufferedReader br = new BufferedReader(new FileReader(this.newXMLAddr));
				FileWriter fw = new FileWriter(this.outputXMLAddr);) {
			br.readLine(); // skip two first lines
			br.readLine();
			fw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<posts>\n");
			for (String prevLine = null, line; (line = br.readLine()) != null;) {
				if (prevLine != null && diffSet.contains(getIDValue(prevLine)))
					fw.write(prevLine + "\n");
				prevLine = line;
			}
			fw.write("</posts>");
		} catch (Exception ¢) {
			System.out.println(¢.getMessage());
		}

		return null;
	}
	
	private Void createDiffDate(){
		LocalDateTime lastDate = getLastDate(this.oldXMLAddr);
		try (BufferedReader br = new BufferedReader(new FileReader(this.newXMLAddr));
				FileWriter fw = new FileWriter(this.outputXMLAddr);) {
			LocalDateTime current;
			fw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<posts>\n");
			br.readLine(); // skip two first lines
			br.readLine();
			for (String prevLine = null, line; (line = br.readLine()) != null;) {
				if (prevLine != null){
					current = getDate(prevLine);
					if(current.isAfter(lastDate) && !current.equals(lastDate))
						fw.write(prevLine + "\n");
				}
				prevLine = line;
			}
			fw.write("</posts>");
		} catch (Exception ¢) {
			System.out.println(¢.getMessage());
		}
		return null;
	}

	/**
	 * Get the date from a single xml line.
	 */
	private static LocalDateTime getDate(String line) {
		return LocalDateTime.parse(line.split(" CreationDate=\"")[1].split("\"")[0],
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
	}

	/**
	 * Get the date of the latest post in the xml file.
	 */
	private static LocalDateTime getLastDate(String xmlAddress) {
		LocalDateTime $ = LocalDateTime.MIN;
		try (BufferedReader br = new BufferedReader(new FileReader(xmlAddress))) {
			for (String line; (line = br.readLine()) != null;)
				if (line.charAt(0) != '<' && $.isBefore(getDate(line)))
					$ = getDate(line);
		} catch (Exception ¢) {
			System.out.println(¢.getMessage());
		}
		return $;
	}
	

	/**
	 * Get a set of all the ids in the xml file.
	 */
	static Set<Integer> getAllIDs(String xmlAddress) {
		Set<Integer> $ = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(xmlAddress))) {
			for (String line; (line = br.readLine()) != null;)
				if (line.charAt(0) != '<')
					$.add(getIDValue(line));
		} catch (Exception ¢) {
			System.out.println(¢.getMessage());
		}
		return $;
	}

	/**
	 * Gets Id value of a xml line
	 */
	static Integer getIDValue(String xml) {
		return Integer.valueOf(xml.split(" Id=\"")[1].split("\"")[0]);
	}

}
