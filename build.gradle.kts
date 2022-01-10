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
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
//        maven {
//            url("https://jitpack.io")
//        }
    }
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}