import CommonApplication.deploymentTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.cocoa)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Core module with common features"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = deploymentTarget
        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "core"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":designsystem"))

                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)

                implementation(compose.components.resources)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.9.6")

            }
        }
        val commonTest by getting {
            dependencies {}
        }
    }
}

android {
    namespace = "com.bunbeauty.core"
    compileSdk = AndroidSdk.compile
    defaultConfig {
        minSdk = AndroidSdk.min
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

}