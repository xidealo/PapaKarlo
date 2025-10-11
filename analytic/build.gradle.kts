import CommonApplication.deploymentTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.cocoa)
    alias(libs.plugins.android.library)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Analytic module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = deploymentTarget

        podfile = project.file("../iosApp/Podfile")

        //pod("FirebaseAnalytics")
        framework {
            baseName = "analytic"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core"))
                implementation(libs.koin.core)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.analytics)
            }
        }
    }
}

android {
    namespace = "com.bunbeauty.analytic"
    compileSdk = AndroidSdk.compile
    defaultConfig {
        minSdk = AndroidSdk.min
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}