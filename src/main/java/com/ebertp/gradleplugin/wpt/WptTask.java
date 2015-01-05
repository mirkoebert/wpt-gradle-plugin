package com.ebertp.gradleplugin.wpt;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class WptTask extends DefaultTask {
	
	private String wptutl = "www.ebert-p.com";
	private int maxdoccomlete = 600;
	
	@TaskAction
    public void wptTask() {
        System.out.println("Java: Hello from wptTask: "+wptutl+" "+maxdoccomlete);
    }
	
	public void wpturl(String para){
		wptutl = para;
	}
	
	public void maxdoccomlete(String para){
		maxdoccomlete = Integer.parseInt(para);
	}

}
