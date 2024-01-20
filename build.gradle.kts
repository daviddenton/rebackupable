import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.google.devtools.ksp.gradle.KspTask
import org.gradle.api.JavaVersion.VERSION_1_8
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    idea
    `java-library`
    application
    id("com.google.devtools.ksp")
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
        classpath("gradle.plugin.com.github.johnrengelman:shadow:_")
    }
}

repositories {
    mavenCentral()
    google()
}

apply(plugin = "kotlin")
apply(plugin = "com.google.devtools.ksp")
apply(plugin = "com.github.johnrengelman.shadow")

apply(plugin = "java")
apply(plugin = "kotlin")

application {
    mainClass.set("daviddenton.MonopolisCLI")
}

repositories {
    mavenCentral()
    google()
}

version = project.properties["releaseVersion"] ?: "LOCAL"
group = "org.http4k"


tasks {
    withType<KspTask> {
        outputs.upToDateWhen { false }
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
            jvmTarget = "1.8"
        }
    }

    java {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<GenerateModuleMetadata> {
        enabled = false
    }

    withType<ShadowJar> {
        archiveBaseName.set("rebackupable")
        archiveClassifier.set("")
        archiveVersion.set("")
        mergeServiceFiles()
    }

    register<JavaExec>("runCli") {
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("monopolis.MonopolisCLI")

        if (project.hasProperty("cliWorkingDir")) {
            workingDir = File(project.property("cliWorkingDir").toString())
        }

        if (project.hasProperty("args")) {
            setArgsString(project.property("args").toString())
        }
    }
}

dependencies {
    testImplementation(Testing.junit.jupiter.api)
    testImplementation(Testing.junit.jupiter.engine)
    testImplementation("com.natpryce:hamkrest:_")
}

configurations.forEach {
    if (it.name.contains("test") || it.name.contains("runtime") || it.name.contains("compileClasspath")) {
        it.exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
    }
}

dependencies {
    api(platform(Http4k.bom))
    api(platform("dev.forkhandles:forkhandles-bom:_"))
    ksp("se.ansman.kotshi:compiler:_")

    api(Http4k.core)
    api(Http4k.cloudnative)
    api(Http4k.client.okhttp)
    api(Http4k.format.moshi)
    api("org.http4k:http4k-format-moshi-yaml")
    api("se.ansman.kotshi:api:_")
    api("com.nimbusds:nimbus-jose-jwt:_")
    api("dev.forkhandles:values4k")
    api("dev.forkhandles:result4k")
    api("com.github.ajalt.clikt:clikt:_")

    testApi(platform(Testing.junit.bom))
    testApi(Testing.junit.jupiter.api)
    testApi(Testing.junit.jupiter.engine)
    testApi(Http4k.testing.approval)
    testApi(Http4k.testing.strikt)
    testApi("com.github.ajalt.clikt:clikt:_")
}
