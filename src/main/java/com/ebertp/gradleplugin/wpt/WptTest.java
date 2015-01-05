package com.ebertp.gradleplugin.wpt;

import org.gradle.api.Plugin;
import org.gradle.api.Project;


public final class WptTest implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		System.out.println("Hello Gradle Plugin User, this is Java.");
	}

}
