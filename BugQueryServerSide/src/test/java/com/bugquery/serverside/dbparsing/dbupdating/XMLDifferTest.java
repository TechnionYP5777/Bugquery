package com.bugquery.serverside.dbparsing.dbupdating;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

/**
 * @author zivizhar
 */
public class XMLDifferTest {

	@Test
	public void testCreateDiff() {
		new XMLDiffer(getClass().getClassLoader().getResource("old.xml").getPath(),
				getClass().getClassLoader().getResource("new.xml").getPath(),
				getClass().getClassLoader().getResource("").getPath() + "/diff.xml").createDiff();
		try (BufferedReader br1 = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("diffcomp.xml").getPath()));
				BufferedReader br2 = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("").getPath() + "/diff.xml"))){
					for (String line1,line2; (line1 = br1.readLine()) != null && (line2 = br2.readLine()) != null;)
						assertEquals(line1, line2);
		} catch(Exception ¢){
			System.out.println(¢.getMessage());
		}

	}

}
