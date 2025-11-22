import org.jetbrains.compose.desktop.application.dsl.JvmApplication
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import proguard.gradle.ProGuardTask

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "1.8.10"
    kotlin("plugin.compose") version "2.0.0"
    id("org.jetbrains.compose") version "1.6.11"
}

val appVersion = "1.0.0"
val icon = project.file("src/main/resources/logo.png")

group = "org.devore.edit"
version = appVersion

val mainClassName = "org.devore.edit.MainKt"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.5.0")
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    implementation(compose.components.resources)
    implementation(compose.components.uiToolingPreview)
    implementation(compose.desktop.currentOs)
    implementation(compose.materialIconsExtended)
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to mainClassName,
                "Manifest-Version" to appVersion
            )
        )
    }
    from(
        configurations.runtimeClasspath.get()
            .filterNot { it.name.lowercase().contains("skiko") && it.name.lowercase().contains("runtime") }.map {
                if (it.isDirectory) it else zipTree(it)
            })
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(sourceSets.main.get().output)
}

compose.desktop {
    application {
        mainClass = mainClassName
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Rpm)
            modules("java.naming", "jdk.management")
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
            packageName = "Devore Edit"
            packageVersion = appVersion
            description = "Devore Edit."
            copyright = "© 2025 Devore. All rights reserved."
            vendor = "RMOlive"
            licenseFile.set(project.file("LICENSE.txt"))
            buildTypes.release.proguard {
                configurationFiles.from("proguard-rules.pro")
            }
            linux {
                iconFile.set(icon)
                debMaintainer = "rmolives@wumoe.org"
                debPackageVersion = appVersion
                rpmPackageVersion = appVersion
            }
            windows {
                iconFile.set(icon)
                packageVersion = appVersion
            }
            macOS {
                iconFile.set(icon)
                dmgPackageVersion = appVersion
            }
        }
        configureProguard()
    }
}

fun JvmApplication.configureProguard() {
    val proguard by tasks.register<ProGuardTask>("proguard") {
        dependsOn(tasks.jar.get())
        injars(tasks.jar.get().outputs.files.singleFile)
        outjars(mapObfuscatedJarFile(tasks.jar.get().outputs.files.singleFile))
        val library = if (System.getProperty("java.version").startsWith("1.")) "lib/rt.jar" else "jmods"
        libraryjars("${compose.desktop.application.javaHome}/$library")
        configuration("proguard-rules.pro")
    }
    disableDefaultConfiguration()
    fromFiles(proguard.outputs.files.asFileTree)
    fromFiles(sourceSets.main.get().runtimeClasspath.filter {
        it.name.lowercase().contains("skiko") && it.name.lowercase().contains("runtime")
    })
    mainJar.set(tasks.jar.map { RegularFile { mapObfuscatedJarFile(it.archiveFile.get().asFile) } })
}

fun mapObfuscatedJarFile(file: File) =
    layout.buildDirectory.dir("obfuscated/${file.nameWithoutExtension}.min.jar").get().asFile