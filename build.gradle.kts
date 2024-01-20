import com.google.devtools.ksp.gradle.KspTask
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    idea
    id("com.google.devtools.ksp")
    id("org.graalvm.buildtools.native") version "0.9.28"
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
    }
}

repositories {
    mavenCentral()
    google()
}

apply(plugin = "kotlin")
apply(plugin = "com.google.devtools.ksp")

apply(plugin = "java")
apply(plugin = "kotlin")

graalvmNative {
    toolchainDetection.set(true)
    binaries {
        named("main") {
            imageName.set("rebackupable")
            mainClass.set("rebackupable.RebackupableCLI")
            useFatJar.set(true)
        }
    }
}

repositories {
    mavenCentral()
    google()
}

tasks {
    withType<KspTask> {
        outputs.upToDateWhen { false }
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    register("osxApp") {
        dependsOn("nativeCompile")
        doLast {
            val dist = "${layout.buildDirectory.get()}/app/${project.name.capitalized()}.app"
            File(dist).deleteRecursively()
            mkdir("$dist/Contents/MacOS")
            mkdir("$dist/Contents/Resources")
            File("src/main/app/Info.plist").copyTo(File("$dist/Contents/Info.plist"))
            File("src/main/app/rebackupable.icns").copyTo(File("$dist/Contents/Resources/rebackupable.icns"))
            File("build/native/nativeCompile/rebackupable").copyTo(File("$dist/Contents/MacOS/rebackupable"))
            Runtime.getRuntime().exec(arrayOf("chmod", "+x", "$dist/Contents/MacOS/rebackupable"))
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
