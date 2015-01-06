package com.ebertp.gradleplugin.wpt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

class WptHelper {

	protected Vector<Integer> extractDoccompleteFromCsvInputStream(InputStream f2) throws IOException{
		Vector<String> doccompleteS = new Vector<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(f2));
        String line;
        boolean firstline =true;
        while ((line = br.readLine()) != null) {
        	if(!firstline){
        		// process the line.
        		String[] result = line.split(",");
        		System.out.println(result[32]);
        		doccompleteS.addElement(result[32]);;
        	}
        	firstline = false;
        }
        br.close();
        
        Vector<Integer> doccomplete = new Vector<Integer>();
        for (String s1 : doccompleteS) {
        	s1 = s1.replaceAll("\"", "");
    		int i1 = Integer.parseInt(s1);
    		doccomplete.add(i1);
		}
        return doccomplete;
	}
	
	protected Vector<Integer> extractDoccompleteFromCSV(File f2)
			throws FileNotFoundException, IOException {
		FileInputStream is = new FileInputStream(f2);
		return extractDoccompleteFromCsvInputStream(is);
	}

}
