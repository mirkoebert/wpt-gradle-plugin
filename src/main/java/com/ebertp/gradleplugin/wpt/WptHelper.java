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
	
	private InputStream is;

	public WptHelper(File resultfile) throws FileNotFoundException {
		is = new FileInputStream(resultfile);
	}

	public WptHelper(InputStream f) {
		is = f;
	}

	protected Vector<Integer> extractDoccomplete(String colnr) throws IOException{
		int col = Integer.parseInt(colnr);
		Vector<String> doccompleteS = new Vector<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        boolean firstline =true;
        while ((line = br.readLine()) != null) {
        	if(!firstline){
        		// process the line.
        		String[] result = line.split(",");
        		doccompleteS.addElement(result[col]);;
        	}
        	firstline = false;
        }
        
        Vector<Integer> doccomplete = new Vector<Integer>();
        for (String s1 : doccompleteS) {
        	s1 = s1.replaceAll("\"", "");
    		int i1 = Integer.parseInt(s1);
    		doccomplete.add(i1);
		}
        return doccomplete;
	}
	

}
