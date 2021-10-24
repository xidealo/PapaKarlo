plugins {
    id(Plugin.application)
    kotlin(Plugin.android)
    id(Plugin.kotlinAndroid)
    id(Plugin.kapt)
    id(Plugin.navigation)
    id(Plugin.googleService)
    id(Plugin.crashlytics)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":domain_api"))
    implementation(project(":domain_firebase"))
    implementation(project(":data_firebase"))
    implementation(project(":data_api"))
    implementation(project(":common"))
    implementation(project(":presentation"))
    implementation(project(":data"))

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")

    implementation(Google.material)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.constraintLayout)

    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUi)
    androidTestImplementation(Navigation.navigationTesting)

    // WorkManager
    implementation(WorkManager.workGcm)
    implementation(WorkManager.workRuntime)

    // Lifecycle
    implementation(Lifecycle.lifecycleExtensions)
    implementation(Lifecycle.lifecycleViewModel)
    implementation(Lifecycle.activity)
    implementation(Lifecycle.fragment)
    implementation(Lifecycle.lifecycleRuntime)
    implementation(Lifecycle.livedata)

    // Dagger 2
    implementation("com.google.dagger:dagger:2.40.1")
    annotationProcessor("com.google.dagger:dagger-compiler:2.40.1")
    implementation("com.google.dagger:dagger-android:2.40.1")
    kapt("com.google.dagger:dagger-compiler:2.40.1")
    kapt("com.google.dagger:dagger-android-processor:2.40.1")

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.test)

    //coroutines
    implementation(Coroutine.coroutineCore)

    //Room
    implementation("androidx.room:room-runtime:2.4.0-rc01")
    implementation("androidx.room:room-ktx:2.4.0-rc01")
    kapt("androidx.room:room-compiler:2.4.0-rc01")

    //Joda time
    implementation(JobaTime.jobaTime)

    implementation(Coil.coilAccompanist)

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:26.7.0"))
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-auth-ktx")

    //PinEntryEditText
    implementation("com.alimuzaffar.lib:pinentryedittext:2.0.6") {
        exclude(group = "androidx.appcompat", module = "appcompat")
    }

    //play:core
    implementation("com.google.android.play:core:1.10.2")

    //ktor
    implementation(Serialization.json)
    implementation(Ktor.clientAndroid)
    implementation(Ktor.clientSerialization)
    implementation(Ktor.clientLogging)
    implementation(Ktor.clientWebsockets)
    implementation(Ktor.clientOkhttp)

    //Timepicker
    implementation("com.wdullaer:materialdatetimepicker:4.2.3")
}


android {

    signingConfigs {
        create("release") {
            storeFile = file("keystore")
            storePassword = "itisBB15092019"
            keyAlias = "papakarloKey"
            keyPassword = "Itispapakarlo08062004"
        }
    }

    compileSdk = AndroidSdk.compile
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
            variant.outputs
                .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                .forEach { output ->
                    val outputFileName =
                        "Fleet_Manager_${variant.baseName}.apk"
                    println("OutputFileName: $outputFileName")
                    output.outputFileName = outputFileName
                }
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            isMinifyEnabled = false
            buildConfigField("String", "FB_LINK", "\"https://test-fooddelivery.firebaseio.com/\"")
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            buildConfigField(
                "String",
                "FB_LINK",
                "\"https://fooddelivery-ce2ef-default-rtdb.firebaseio.com/\""
            )
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

}

