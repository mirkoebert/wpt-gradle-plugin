package com.ebertp.gradleplugin.wpt;

import java.net.URL;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.w3c.dom.Document;

public class WptTask extends DefaultTask {
	
	private String testurl = "www.ebert-p.com";
	private int maxdoccomlete = 600;
	private String wpturl="http://set-wpt-server-as-gradle-task-parameter.error";
	private String location="set-wpt-location-as-gradle-task-parameter-error";
	private boolean failOnError = false;
	
	@TaskAction
    public void wptTask() throws Exception {
		String completeUrl = wpturl + "/runtest.php?url="+testurl+"&f=xml&runs=1&video=0&web10=0&noscript=0&clearcerts=0&ignoreSSL=0&standards=0&tcpdump=0&bodies=0&continuousVideo=0&label=label-of-measurement&location="+location;
		System.out.println("Java: Hello from wptTask: "+testurl+" "+maxdoccomlete);
       
        URL url = new URL(completeUrl);
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(url.openStream());
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//summaryCSV/text()");
        String u = expr.evaluate(doc);
        
        Thread.sleep(55000);

        URL urlu = new URL(u);
        WptHelper wh = new WptHelper();
        Vector<Integer> doccomplete = wh.extractDoccompleteFromCsvInputStream(urlu.openStream());
        
        int testresult = 0;
        for (int x : doccomplete) {
			if(x>maxdoccomlete){
				testresult++;
			}
		}
        
        
        String errormesg = "WPT test time limit for doccomplete exceeded: "+testresult+" times.";
        if(failOnError){
        	throw new Exception(errormesg);
        } else {
        	System.out.println(errormesg);
        }
    }

	
	public void testurl(String para){
		testurl = para;
	}
	
	public void maxdoccomlete(String para){
		maxdoccomlete = Integer.parseInt(para);
	}

	public void wpturl(String para){
		// TODO remove trailing slash
		wpturl = para;
	}
	
	
	public void location(String para){
		location = para;
	}

	public void failOnError(String para){
		failOnError = Boolean.parseBoolean(para);
	}
}

