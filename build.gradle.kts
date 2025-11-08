// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.application) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.triplet.play) apply false
    alias(libs.plugins.google.service) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.ktLint) apply false
    alias(libs.plugins.multiplatform) apply false
    alias(libs.plugins.cocoa) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.mokkery) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.library) apply false
}

buildscript {
    repositories {
        google().content {
            excludeGroup("Kotlin/Native")
        }
        mavenCentral().content {
            excludeGroup("Kotlin/Native")
        }

        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.google.services)
        classpath(libs.kotlin.serialization)
        classpath(libs.firebase.crashlytics.gradle)
        classpath(libs.sqlDelight.gradle.plugin)
        classpath(libs.play.publisher)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

