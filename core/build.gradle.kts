import Constants.DEPLOYMENT_TARGET

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Core module with common features"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = DEPLOYMENT_TARGET
        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "core"
            isStatic = true
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Koin.core)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}