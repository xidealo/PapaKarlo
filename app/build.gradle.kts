import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    kotlin(Plugin.android)
    id(Plugin.application)
    id(Plugin.kotlinAndroid)
    id(Plugin.kapt)
    id(Plugin.navigation)
    id(Plugin.googleService)
    id(Plugin.crashlytics)
}

android {

    compileSdk = AndroidSdk.compile

    signingConfigs {
        create("release") {
            storeFile = file("keystore")
            storePassword = "itisBB15092019"
            keyAlias = "papakarloKey"
            keyPassword = "Itispapakarlo08062004"
        }
    }

    defaultConfig {
        applicationId = Application.applicationId
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        versionCode = Application.versionCode
        versionName = Application.versionName
    }
    buildTypes {
        applicationVariants.all {
            val variant = this
            variant.outputs.forEach { output ->
                (output as BaseVariantOutputImpl).outputFileName =
                    "FoodDelivery_${variant.baseName}.apk"
            }
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            isMinifyEnabled = false
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":domain_api"))
    implementation(project(":data_api"))
    implementation(project(":common"))
    implementation(project(":presentation"))
    implementation(project(":data"))

    implementation(Google.material)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.constraintLayout)

    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationFragmentKtx)
    implementation(Navigation.navigationRuntime)
    implementation(Navigation.navigationRuntimeKtx)
    implementation(Navigation.navigationUI)
    implementation(Navigation.navigationUIKtx)
    androidTestImplementation(Navigation.navigationTesting)

    implementation(Lifecycle.extensions)
    implementation(Lifecycle.viewmodel)
    implementation(Lifecycle.activity)
    implementation(Lifecycle.fragment)
    implementation(Lifecycle.runtime)
    implementation(Lifecycle.livedate)

    implementation(ViewBindingDelegate.viewBindingDelegate)

    implementation(Dagger.dagger)
    annotationProcessor(Dagger.compiler)
    implementation(Dagger.android)
    kapt(Dagger.compiler)
    kapt(Dagger.androidProcessor)

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.test)

    implementation(Serialization.json)
    implementation(Ktor.clientSerialization)
    implementation(Ktor.clientLogging)
    implementation(Ktor.clientWebsockets)
    implementation(Ktor.clientOkhttp)

    implementation(WorkManager.workGcm)
    implementation(WorkManager.workRuntime)

    implementation(Coroutine.core)

    implementation(Room.runtime)
    implementation(Room.roomKtx)
    kapt(Room.compiler)

    implementation(JodaTime.jodaTime)

    implementation(platform(Firebase.bom))
    implementation(Firebase.crashlyticsKtx)
    implementation(Firebase.analyticsKtx)
    implementation(Firebase.auth)
    implementation(Firebase.authKtx)

    implementation(Coil.coil)

    implementation(PinEntryEditText.pinEntryEditText) {
        exclude(group = PinEntryEditText.group, module = PinEntryEditText.module)
    }

    implementation(TimePicker.timePicker)

    debugImplementation(Leakcanary.android)
}