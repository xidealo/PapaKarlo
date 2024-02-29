import Constants.DEPLOYMENT_TARGET
import Constants.DJAN_FLAVOR_NAME
import Constants.GUSTO_PUB_FLAVOR_NAME
import Constants.PAPA_KARLO_FLAVOR_NAME
import Constants.TANDIR_HOUSE_FLAVOR_NAME
import Constants.YULIAR_FLAVOR_NAME

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
        summary = "Analytic module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = DEPLOYMENT_TARGET

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
                implementation(Koin.core)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(project.dependencies.platform(Firebase.bom))
                implementation(Firebase.analyticsKtx)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
    }
    setFlavorDimensions(listOf("default"))
    productFlavors {
        create(PAPA_KARLO_FLAVOR_NAME) {}
        create(YULIAR_FLAVOR_NAME) {}
        create(DJAN_FLAVOR_NAME) {}
        create(GUSTO_PUB_FLAVOR_NAME) {}
        create(TANDIR_HOUSE_FLAVOR_NAME) {}
    }
}