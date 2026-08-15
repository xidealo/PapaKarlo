import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

configurations.configureEach {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlinx" &&
            requested.name.startsWith("kotlinx-coroutines")
        ) {
            useVersion("1.8.1")
            because("Align kotlinx-coroutines with AGP SDK loader on the plugin classpath")
        }
    }
}
