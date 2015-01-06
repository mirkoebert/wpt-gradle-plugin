wpt-gradle-plugin
=================

Gradle Plugin to use WPT as an test tool.



Usage
-----

	buildscript {
        	repositories {
                	maven { url "http://nexus.yourcompany.de:8080/content/groups/public" }
        	}

        	dependencies {
                	classpath "de.otto:lpt-gradle-plugin:0.0.19-SNAPSHOT"
			//classpath files('../wpt-gradle-plugin/target/lpt-gradle-plugin-0.0.19-SNAPSHOT.jar')
        	}
	}

	apply plugin: "lpt-gradle"



	task testWptDoccomplete(type: com.ebertp.gradleplugin.wpt.WptTask, group: "WPT") {
        	description     'Simple WPT test, checks Doccomplete time.'
        	testurl         'www.otto.de'
        	wpturl          'http://wpt.yourcompany.de'
        	//maxdoccomlete '600' // msec
        	location        'yourwptlocation:Firefox.Native'
	}
