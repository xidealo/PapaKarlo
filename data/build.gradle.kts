plugins {
    kotlin(Plugin.android)
    id(Plugin.androidLibrary)
    id(Plugin.kapt)
    id(Plugin.kotlinAndroid)
    id(Plugin.kotlinSerialization)
}

android {

    compileSdk = AndroidSdk.compile

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    implementation(Room.runtime)
    implementation(Room.roomKtx)
    kapt(Room.compiler)

    implementation(DataStore.dataStorePreferences)

    implementation(Dagger.dagger)
    annotationProcessor(Dagger.compiler)
    implementation(Dagger.android)
    kapt(Dagger.compiler)
    kapt(Dagger.androidProcessor)

    implementation(platform(Firebase.bom))
    implementation(Firebase.authKtx)

    implementation(Serialization.json)
    implementation(Ktor.clientSerialization)
    implementation(Ktor.clientLogging)
    implementation(Ktor.clientWebsockets)
    implementation(Ktor.clientOkhttp)
}