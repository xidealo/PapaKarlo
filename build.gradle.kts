// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    // Must stay on 1.8.x: AGP RepoManager is compiled against that bytecode.
    // Mixing 1.10+/1.11 on the plugin classpath breaks IDE sync (AbstractTimeSource).
    val pluginClasspathCoroutines = "1.8.1"
    dependencies {
        classpath("org.jetbrains.kotlinx:kotlinx-coroutines-core:$pluginClasspathCoroutines")
        classpath("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$pluginClasspathCoroutines")
    }
    configurations.classpath {
        resolutionStrategy {
            eachDependency {
                if (requested.group == "org.jetbrains.kotlinx" &&
                    requested.name.startsWith("kotlinx-coroutines")
                ) {
                    useVersion(pluginClasspathCoroutines)
                    because("Single kotlinx-coroutines version on the plugin classpath for AGP SDK loader")
                }
            }
        }
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
