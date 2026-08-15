import CommonApplication.deploymentTarget
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.cocoa)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
}

kotlin {
    applyDefaultHierarchyTemplate()
    android {
        namespace = "com.bunbeauty.designsystem"
        compileSdk = AndroidSdk.compile
        minSdk = AndroidSdk.min
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
        androidResources {
            enable = true
        }
        withHostTest {}
        withDeviceTest {}
    }

    iosArm64()
    iosSimulatorArm64()

    js(IR) {
        browser()
    }

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
                implementation(compose.uiTooling)
            }
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = auto
}
