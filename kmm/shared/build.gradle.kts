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
    iosSimulatorArm64() //sure all ios dependencies support this target

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":kmm:core:core-common"))
                Ktor.run {
                    implementation(clientSerialization)
                    implementation(clientLogging)
                    implementation(clientWebsockets)
                    //implementation(clientOkhttp)
                }

                implementation(Coroutine.core)

                implementation(Serialization.json)
                // koin
                implementation(Koin.core)
                api(Koin.test)

                implementation(KotlinxDateTime.dateTime)

                implementation(project.dependencies.platform(Firebase.bom))
                implementation(Firebase.authKtx)

                implementation(SqlDelight.runtime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(DataStore.dataStorePreferences)

                implementation(SqlDelight.androidDriver)
                implementation(SqlDelight.coroutineExtensions)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(SqlDelight.nativeDriver)
            }
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
}

sqldelight {
    database("FoodDeliveryDatabase") {
        packageName = "com.bunbeauty.shared.data"
    }
}