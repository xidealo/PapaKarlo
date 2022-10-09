object Versions {
    const val gradle = "7.2.2"
    const val kotlin = "1.7.20"
    const val googleServices = "4.3.10"
    const val crashlytics = "2.8.1"
    const val navigation = "2.5.1"

    const val kotlinCoroutines = "1.6.4"
    const val ktor = "2.1.2"
    const val kotlinxSerialization = "1.3.2"
    const val koin = "3.1.5"

    const val sqlDelight = "1.5.3"

    const val constraintLayout = "2.1.3"

    const val junit = "4.13.2"
    const val testRunner = "1.3.0"
    const val material = "1.4.0"
    const val appCompact = "1.5.1"
    const val coreKtx = "1.9.0"
    const val coil = "2.1.0"
    const val timePicker = "4.2.3"

    const val composeCompiler = "1.3.2"
    const val composeMaterial = "1.2.1"
    const val composeAnimation = "1.2.1"
    const val composeUi = "1.2.1"
    const val composeThemeAdapter = "1.1.4"

    const val dataStorePreferences = "1.0.0"

    const val extensions = "2.2.0"
    const val viewmodel = "2.4.1"
    const val activity = "1.4.0"
    const val fragment = "1.4.0"
    const val lifecycle = "2.4.0"

    const val viewBindingDelegate = "1.5.3"

    const val googleMapUtils = "2.2.3"

    const val kotlinxDateTime = "0.3.3"

    const val leakcanary = "2.8.1"

    const val firebase = "29.0.3"

    const val pinEntryEditText = "2.0.6"
}

object Application {
    const val versionMajor = 1
    const val versionMinor = 2
    const val versionPatch = 3

    const val versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
    const val applicationId = "com.bunbeuaty.papakarlo"
    const val versionCode = 123
}

object AndroidSdk {
    const val min = 24
    const val compile = 33
    const val target = compile
}

object ClassPath {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
    const val firebaseCrashlyticsGradle =
        "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics}"
    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
}

object Plugin {
    const val application = "com.android.application"
    const val android = "android"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinParcelize = "kotlin-parcelize"
    const val kotlinSerialization = "kotlinx-serialization"
    const val kapt = "kotlin-kapt"
    const val navigation = "androidx.navigation.safeargs"
    const val multiplatform = "multiplatform"
    const val googleService = "com.google.gms.google-services"
    const val crashlytics = "com.google.firebase.crashlytics"
    const val sqldelight = "com.squareup.sqldelight"
}

object AndroidX {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompact}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
}

object Google {
    const val material = "com.google.android.material:material:${Versions.material}"
    const val androidMaps = "com.google.maps.android:android-maps-utils:${Versions.googleMapUtils}"
}

object Compose {
    const val material = "androidx.compose.material:material:${Versions.composeMaterial}"
    const val animation = "androidx.compose.animation:animation:${Versions.composeAnimation}"
    const val ui = "androidx.compose.ui:ui-tooling:${Versions.composeUi}"
    const val materialThemeAdapter =
        "com.google.android.material:compose-theme-adapter:${Versions.composeThemeAdapter}"
}

object Lifecycle {
    const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.extensions}"
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewmodel}"
    const val viewmodelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewmodel}"
    const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activity}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val livedate = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
}

object Test {
    const val junit = "junit:junit:${Versions.junit}"
}

object Koin {
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val test = "io.insert-koin:koin-test:${Versions.koin}"
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
    const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
}

object Navigation {
    const val navigationFragment =
        "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationRuntime = "androidx.navigation:navigation-runtime:${Versions.navigation}"
    const val navigationRuntimeKtx =
        "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
    const val navigationUI =
        "androidx.navigation:navigation-ui:${Versions.navigation}"
    const val navigationUIKtx =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationTesting = "androidx.navigation:navigation-testing:${Versions.navigation}"
}

object Ktor {

    const val clientWebsockets = "io.ktor:ktor-client-websockets:${Versions.ktor}"
    const val negotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"

    const val clientJson = "io.ktor:ktor-client-json:${Versions.ktor}"
    const val serializerJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"

    const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val clientAuth = "io.ktor:ktor-client-auth:${Versions.ktor}"

    const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val clientIos = "io.ktor:ktor-client-ios:${Versions.ktor}"
    const val clientOkhttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"
}

object TimePicker {
    const val timePicker = "com.wdullaer:materialdatetimepicker:${Versions.timePicker}"
}

object KotlinxDateTime {
    const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDateTime}"
}

object DataStore {
    const val dataStorePreferences =
        "androidx.datastore:datastore-preferences:${Versions.dataStorePreferences}"
}

object Serialization {
    const val json =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
}

object Firebase {
    const val bom = "com.google.firebase:firebase-bom:${Versions.firebase}"
    const val crashlyticsKtx = "com.google.firebase:firebase-crashlytics-ktx"
    const val analyticsKtx = "com.google.firebase:firebase-analytics-ktx"
    const val auth = "com.google.firebase:firebase-auth"
    const val authKtx = "com.google.firebase:firebase-auth-ktx"
}

object PinEntryEditText {
    const val pinEntryEditText = "com.alimuzaffar.lib:pinentryedittext:${Versions.pinEntryEditText}"
    const val group = "androidx.appcompat"
    const val module = "appcompat"
}

object SqlDelight {
    const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
    const val coroutineExtensions =
        "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
    const val androidDriver = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"

    const val nativeDriver = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
}

object Coroutine {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
}

object ViewBindingDelegate {
    const val viewBindingDelegate =
        "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBindingDelegate}"
}

object Leakcanary {
    const val android = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
}
