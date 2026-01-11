import CommonApplication.deploymentTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.cocoa)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
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

        framework {
            baseName = "analytic"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)

                implementation(compose.components.resources)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.kotlinx.datetime)
                implementation(libs.bundles.coil)
                implementation(libs.kotlinx.collections.immutable)

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
    namespace = "com.bunbeauty.designsystem"
    compileSdk = AndroidSdk.compile
    defaultConfig {
        minSdk = AndroidSdk.min
    }
    compose.resources {
        publicResClass = true
        generateResClass = auto
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}