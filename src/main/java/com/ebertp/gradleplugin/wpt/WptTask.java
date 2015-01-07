package com.ebertp.gradleplugin.wpt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class WptTask extends DefaultTask {

	private Properties prop = new Properties();
	private String testurl = "www.ebert-p.com";
	private int doccomlete = -1;
	private String wpturl="http://set-wpt-server-as-gradle-task-parameter.error";
	private String location="set-wpt-location-as-gradle-task-parameter-error";
	private boolean failOnError = false;
	private int ttfb = -1;
	private boolean occursErrors = false;
	
	@TaskAction
    public void wptTask() throws Exception {
		String completeUrl = init();       
        String u = runRemoteWptTest(completeUrl);

        URL urlu = new URL(u);
        File resultfile = new File("wpt_test_data.csv");
        FileUtils.copyURLToFile(urlu, resultfile);
        WptHelper wh = new WptHelper(resultfile);
        
        if(doccomlete >-1){
        	System.out.println("Test: doccomplete: "+doccomlete+" msec");
        	Vector<Integer> doccompletelist = wh.extractDoccomplete(prop.getProperty("doccomlete"));
        	int testresult = checkWptResults(doccompletelist);
        	handleWptResults(testresult, "doccomplete");
        }
        if(ttfb >-1){
        	System.out.println("Test: ttfb: "+ttfb+" msec");
        	Vector<Integer> doccompletelist = wh.extractDoccomplete(prop.getProperty("ttfb"));
        	int testresult = checkWptResults(doccompletelist);
        	handleWptResults(testresult, "ttfb");
        }
        
        
        String errormesg ="WPT test fails.";
		if(failOnError&&occursErrors){
        	throw new Exception(errormesg );
        } else {
        	System.out.println(errormesg);
        }
        
        
        
    }


	private void handleWptResults(int testresult, String name) throws IOException,
			Exception {
		String errormesg = "WPT test time limit for "+name+" exceeded: "+testresult+" times.";
		System.out.println(errormesg);
	}


	private int checkWptResults(Vector<Integer> doccompletelist) {
		int testresult = 0;
        for (int x : doccompletelist) {
			if(x>doccomlete){
				testresult++;
			}
		}
        if(!doccompletelist.isEmpty()){
        	System.out.println(doccompletelist);
        }
		return testresult;
	}


	private String runRemoteWptTest(String completeUrl)
			throws MalformedURLException, ParserConfigurationException,
			SAXException, IOException, XPathExpressionException,
			InterruptedException {
		URL url = new URL(completeUrl);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(url.openStream());
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//summaryCSV/text()");
        String u = expr.evaluate(doc);
        System.out.println("WPT Result URL "+u);
        Thread.sleep(55000);
		return u;
	}


	private String init() throws IOException {
		String completeUrl = wpturl + "/runtest.php?url="+testurl+"&f=xml&runs=1&video=0&web10=0&noscript=0&clearcerts=0&ignoreSSL=0&standards=0&tcpdump=0&bodies=0&continuousVideo=0&label=label-of-measurement&location="+location;
		System.out.println("Java: Hello from wptTask: "+testurl+" "+doccomlete);
		//System.out.println("p: "+ WptTask.class.getResource("/wpt_result_col_mapping.properties"));
		InputStream stream = WptTask.class.getResourceAsStream("/wpt_result_col_mapping.properties");
		prop.load(stream);
		return completeUrl;
	}

	
	public void testurl(String para){
		testurl = para;
	}
	
	public void doccomlete(String para){
		doccomlete = Integer.parseInt(para);
	}

	public void ttfb(String para){
		ttfb  = Integer.parseInt(para);
	}

	public void wpturl(String para){
		wpturl = para;
	}
	
	
	public void location(String para){
		location = para;
	}

	public void failOnError(String para){
		failOnError = Boolean.parseBoolean(para);
	}
	
}