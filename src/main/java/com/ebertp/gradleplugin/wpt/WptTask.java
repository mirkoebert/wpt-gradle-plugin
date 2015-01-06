package com.ebertp.gradleplugin.wpt;

import java.io.File;
import java.net.URL;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.w3c.dom.Document;

public class WptTask extends DefaultTask {
	
	private String testurl = "www.ebert-p.com";
	private int maxdoccomlete = 600;
	private String wpturl="http://set-wpt-server-as-gradle-task-parameter.error";
	
	@TaskAction
    public void wptTask() throws Exception {
		String completeUrl = wpturl + "/runtest.php?url="+testurl+"&f=xml&runs=1&video=0&web10=0&noscript=0&clearcerts=0&ignoreSSL=0&standards=0&tcpdump=0&bodies=0&continuousVideo=0&label=label-of-measurement&location=otto-exp-netlab:Firefox.Native";
		System.out.println("Java: Hello from wptTask: "+testurl+" "+maxdoccomlete);
       
        URL url = new URL(completeUrl);
        File f1 = new File("job.xml");
        FileUtils.copyURLToFile(url, f1 );
        
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(f1);
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//summaryCSV/text()");
        String u = expr.evaluate(doc);
        System.out.println("u: "+u);
        
        Thread.sleep(55000);

        URL urlu = new URL(u);
        File f2 = new File("page_data.csv");
        FileUtils.copyURLToFile(urlu, f2 );
        
        WptHelper wh = new WptHelper();
        Vector<Integer> doccomplete = wh.extractDoccompleteFromCSV(f2);
        
        int testresult = 0;
        for (int x : doccomplete) {
			if(x>maxdoccomlete){
				testresult++;
			}
		}
        System.out.println("Test Result: " +testresult);
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
}
