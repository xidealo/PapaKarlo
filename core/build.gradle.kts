import CommonApplication.deploymentTarget
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.cocoa)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()

    android {
        namespace = "com.bunbeauty.core"
        compileSdk = AndroidSdk.compile
        minSdk = AndroidSdk.min
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
        androidResources {
            enable = true
        }
    }

    iosArm64()
    iosSimulatorArm64()

    js(IR) {
        browser()
    }

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
                implementation(libs.bundles.lifecycle)

                implementation(compose.components.resources)
                implementation(compose.foundation)
                implementation(compose.material3)
            }
        }
        val commonTest by getting {
            dependencies {}
        }
    }
}
