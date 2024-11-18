import CommonApplication.deploymentTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.cocoa)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.mokkery)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

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

                implementation(libs.bundles.ktor)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.kotlinx.serialization.json)

                implementation(libs.koin.core)
                api(libs.koin.test)

                implementation(libs.kotlinx.datetime)

                implementation(libs.sqlDelight.runtime)
                implementation(libs.sqlDelight.coroutines.extensions)

                implementation(libs.kotlinx.collections.immutable)
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

                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.messaging.ktx)
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
        val iosMain by creating {
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
        val iosTest by creating {
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
    defaultConfig {
        minSdk = AndroidSdk.min
        compileSdk = AndroidSdk.compile
    }
    buildTypes {
        debug {}
        release {}
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    database("FoodDeliveryDatabase") {
        packageName = "com.bunbeauty.shared.db"
    }
}
