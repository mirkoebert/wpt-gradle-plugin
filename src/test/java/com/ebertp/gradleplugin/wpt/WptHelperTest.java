package com.ebertp.gradleplugin.wpt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class WptHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExtractDoccompleteFromCSV() throws IOException{
		URL u = ClassLoader.getSystemResource("page_data.csv");
		System.out.println("XX "+u);
		InputStream f = ClassLoader.getSystemResourceAsStream("page_data.csv");

		WptHelper wh = new WptHelper(f);
		Vector<Integer> v = wh.extractDoccomplete("32");
		assertTrue(v.size()>0);
		assertEquals(693, v.get(0).intValue());
	}

}
