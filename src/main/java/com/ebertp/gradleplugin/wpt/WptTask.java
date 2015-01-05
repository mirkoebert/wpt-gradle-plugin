package com.ebertp.gradleplugin.wpt;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class WptTask extends DefaultTask {
	
	@TaskAction
    public void wptTask() {
        System.out.println("Hello from wptTask.");
    }

}
