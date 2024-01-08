pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

buildscript {
    dependencies {
        classpath("com.talk2duck:gradle-s3-build-cache:1.6.0.1")
    }
}

plugins {
    id("de.fayard.refreshVersions").version("0.60.3")
}

refreshVersions {
    enableBuildSrcLibs()

    rejectVersionIf {
        candidate.stabilityLevel.isLessStableThan(current.stabilityLevel)
    }
}

rootProject.name = "rebackupable"
