package com.ebertp.gradleplugin.wpt;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class WptHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExtractDoccompleteFromCSV() throws IOException{
		WptHelper wh = new WptHelper();
		File f = new File("page_data.csv");
		Vector<Integer> v = wh.extractDoccompleteFromCSV(f);
		assertTrue(v.size()>0);
		//String s1 = v.get(0);
		//s1 = s1.replaceAll("\"", "");
		//int i1 = Integer.parseInt(s1);
		assertEquals(677, v.get(0).intValue());
	}

}
