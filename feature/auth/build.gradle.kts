import CommonApplication.deploymentTarget

plugins {
    alias(libs.plugins.client.compose.multiplatform.feature)
    alias(libs.plugins.cocoa)
}

kotlin {
    cocoapods {
        summary = "Main shared module with presentation layer"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = deploymentTarget
        podfile = project.file("../iosApp/Podfile")

        pod("FirebaseMessaging")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":designsystem"))
                implementation(project(":analytic"))
                implementation(project(":core"))

                implementation(libs.kotlinx.collections.immutable)

                implementation(compose.components.resources)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.animation)
                implementation(compose.animationGraphics)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.bundles.navigation)
                implementation(libs.bundles.di)
                implementation(libs.bundles.coil)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.activity.compose)
                implementation(compose.uiTooling)
                implementation(libs.firebase.messaging)

            }
        }
    }
}

android {
    namespace = "com.bunbeauty.feature.auth"
}