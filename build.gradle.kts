// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google().content {
            excludeGroup("Kotlin/Native")
        }
        mavenCentral().content {
            excludeGroup("Kotlin/Native")
        }
    }
    dependencies {
        classpath(ClassPath.gradle)
        classpath(ClassPath.kotlinGradlePlugin)
        classpath(ClassPath.googleServices)
        classpath(ClassPath.kotlinSerialization)
        classpath(ClassPath.firebaseCrashlyticsGradle)
        classpath(ClassPath.navigationSafeArgs)
        classpath(ClassPath.sqlDelight)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
