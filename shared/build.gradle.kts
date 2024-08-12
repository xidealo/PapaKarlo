import Constants.DEPLOYMENT_TARGET

plugins {
    kotlin(Plugin.multiplatform)
    kotlin("native.cocoapods")
    id(Plugin.androidLibrary)
    id(Plugin.sqldelight)
    id(Plugin.kotlinSerialization)
    id(Plugin.kapt)
    id("dev.mokkery") version "2.2.0"
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

                implementation(Koin.core)
                api(Koin.test)

                implementation(KotlinxDateTime.dateTime)

                implementation(SqlDelight.runtime)
                implementation(SqlDelight.coroutineExtensions)

                implementation(CollectionsImmutable.collectionsImmutable)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Coroutine.test)

                implementation(kotlin("test"))
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

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, "io.mockative:mockative-processor:2.2.2")
        }
}
