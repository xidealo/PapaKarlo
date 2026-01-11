import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.bunbeauty.buildlogic"

// Configure the build-logic plugins to target JDK 21
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
}

dependencies {
    implementation(gradleKotlinDsl())
    compileOnly(libs.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
    implementation(kotlin("test"))
}

tasks {
    validatePlugins {
        enableStricterValidation.assign(true)
        failOnWarning.assign(true)
    }
}

gradlePlugin {
    plugins {
        register("multiplatformFeature") {
            id = "com.bunbeauty.compose.multiplatform.feature"
            version = "1.0"
            implementationClass = "com.bunbeauty.conventions.ComposeMultiplatformFeatureConventionPlugin"
        }
        register("androidApplication") {
            id = "com.bunbeauty.android.application"
            version = "1.0"
            implementationClass = "com.bunbeauty.conventions.AndroidApplicationConventionPlugin"
        }
    }
}
