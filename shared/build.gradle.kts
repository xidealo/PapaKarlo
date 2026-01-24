import CommonApplication.deploymentTarget

plugins {
    alias(libs.plugins.cocoa)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.client.compose.multiplatform.feature)
    alias(libs.plugins.mokkery)
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
                implementation(project(":analytic"))
                implementation(project(":core"))
                implementation(project(":designsystem"))
                implementation(project(":feature:menu"))
                implementation(project(":feature:profile"))
                implementation(project(":feature:productdetails"))
                implementation(project(":feature:auth"))
                implementation(project(":feature:address"))
                implementation(project(":feature:splash"))
                implementation(project(":feature:cafe"))
                implementation(project(":feature:update"))
                implementation(project(":feature:order"))
                implementation(project(":feature:createorder"))
                implementation(project(":feature:consumercart"))

                implementation(libs.bundles.ktor)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.kotlinx.serialization.json)

                implementation(libs.kotlinx.datetime)

                implementation(libs.sqlDelight.runtime)
                implementation(libs.sqlDelight.coroutines.extensions)

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
                api(libs.koin.test)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)

                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.datastore.preferences)
                implementation(libs.lifecycle.viewmodel.ktx)

                implementation(libs.activity.compose)
                implementation(compose.uiTooling)
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.messaging)
                implementation(libs.sqlDelight.android)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                implementation(libs.sqlDelight.native)
                implementation(libs.ktor.client.darwin)
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by getting {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = Namespace.shared
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

sqldelight {
    database("FoodDeliveryDatabase") {
        packageName = "com.bunbeauty.shared.db"
    }
}
