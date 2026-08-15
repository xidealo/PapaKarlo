import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.kmp.library)
}

kotlin {
    applyDefaultHierarchyTemplate()

    android {
        namespace = "com.bunbeauty.di"
        compileSdk = AndroidSdk.compile
        minSdk = AndroidSdk.min
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
        withHostTest {}
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
            }
        }
        val commonTest by getting {
            dependencies {}
        }
    }
}
