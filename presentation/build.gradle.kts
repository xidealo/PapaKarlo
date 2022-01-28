plugins {
    kotlin(Plugin.android)
    id(Plugin.androidLibrary)
    id(Plugin.kotlinAndroid)
    id(Plugin.kapt)
    id(Plugin.kotlinParcelize)
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

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Google.material)

    implementation(Lifecycle.extensions)
    implementation(Lifecycle.viewmodel)


    implementation(Dagger.dagger)
    annotationProcessor(Dagger.compiler)
    implementation(Dagger.android)
    kapt(Dagger.compiler)
    kapt(Dagger.androidProcessor)

    implementation(Coroutine.core)

    implementation(JodaTime.jodaTime)
}