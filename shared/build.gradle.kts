plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id(Plugin.sqldelight)
    id(Plugin.kotlinSerialization)
}

version = "1.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        val firebaseVersion = "9.5.0"

        pod("FirebaseAuth") {
            version = firebaseVersion
        }

        framework {
            baseName = "shared"
            isStatic = false
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
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

                implementation(Firebase.authKtx)
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
    setFlavorDimensions(listOf("default"))
    productFlavors {
        create("papaKarlo") {}
        create("yuliar") {}
    }
}

sqldelight {
    database("FoodDeliveryDatabase") {
        packageName = "com.bunbeauty.shared.db"
    }
}