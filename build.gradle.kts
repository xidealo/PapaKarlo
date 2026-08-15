// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    alias(libs.plugins.application) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose) apply false
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
    alias(libs.plugins.android.kmp.library) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
