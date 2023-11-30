import Constants.DEPLOYMENT_TARGET
import Constants.DJAN_FLAVOR_NAME
import Constants.GUSTO_PUB_FLAVOR_NAME
import Constants.PAPA_KARLO_FLAVOR_NAME
import Constants.YULIAR_FLAVOR_NAME

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id(Plugin.sqldelight)
    id(Plugin.kotlinSerialization)
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
        ios.deploymentTarget = DEPLOYMENT_TARGET
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

                Ktor.run {
                    implementation(clientSerialization)
                    implementation(clientLogging)
                    implementation(clientWebsockets)
                    implementation(negotiation)
                    implementation(serializerJson)
                    implementation(clientJson)
                    implementation(clientAuth)
                }
                implementation(Coroutine.core)

                implementation(Serialization.json)
                // koin
                implementation(Koin.core)
                api(Koin.test)

                implementation(KotlinxDateTime.dateTime)

                implementation(SqlDelight.runtime)
                implementation(SqlDelight.coroutineExtensions)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(MockK.main)
                implementation(MockK.common)
                implementation(Coroutine.test)

                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(DataStore.dataStorePreferences)
                implementation(Ktor.clientOkhttp)
                implementation(Lifecycle.viewmodel)

                implementation(project.dependencies.platform(Firebase.bom))
                implementation(Firebase.messaging)

                implementation(SqlDelight.androidDriver)
            }
        }
        //val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation(SqlDelight.nativeDriver)
                implementation(Ktor.clientDarwin)
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
        targetSdk = AndroidSdk.target
    }
    buildTypes {
        debug {}
        release {}
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    setFlavorDimensions(listOf("default"))
    productFlavors {
        create(PAPA_KARLO_FLAVOR_NAME) {}
        create(YULIAR_FLAVOR_NAME) {}
        create(DJAN_FLAVOR_NAME) {}
        create(GUSTO_PUB_FLAVOR_NAME) {}
    }
}

sqldelight {
    database("FoodDeliveryDatabase") {
        packageName = "com.bunbeauty.shared.db"
    }
}