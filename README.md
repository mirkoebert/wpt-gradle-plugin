wpt-gradle-plugin
=================

Gradle Plugin to use WPT as an test tool.



Usage
-----


buildscript {
        repositories {
                maven { url "http://nexus.yourcompany.de:8080/content/groups/public" }
                maven { url "http://repo.gradle.org/gradle/libs-releases-local" }
        }

        dependencies {
                classpath "de.otto:lpt-gradle-plugin:0.0.19-SNAPSHOT"
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
