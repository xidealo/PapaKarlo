plugins {
    kotlin(Plugin.android)
    id(Plugin.androidLibrary)
    id(Plugin.kapt)
    id(Plugin.kotlinAndroid)
    id(Plugin.kotlinParcelize)
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
    implementation(project(":common"))
    implementation(project(":kmm:core:core-common"))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)

    implementation(Coroutine.core)

    implementation(KotlinxDateTime.dateTime)

    implementation(Test.junit)

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.test)

}