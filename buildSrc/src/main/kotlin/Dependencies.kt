object Versions {
    const val gradle = "7.0.2"
    const val kotlin = "1.6.10"
    const val googleServices = "4.3.10"
    const val crashlytics = "2.8.1"
    const val navigation = "2.4.0"

    const val kotlinCoroutines = "1.6.0-native-mt"
    const val ktor = "1.6.5"
    const val kotlinxSerialization = "1.3.2"
    const val koin = "3.1.5"

    const val sqlDelight = "1.5.3"
    const val slf4j = "1.7.30"

    const val constraintLayout = "2.1.3"

    const val dagger = "2.40.1"

    const val room = "2.4.0-rc01"
    const val junit = "4.13.2"
    const val testRunner = "1.3.0"
    const val material = "1.4.0"
    const val materialComposeThemeAdapter = "1.1.4"
    const val appCompact = "1.4.1"
    const val coil = "2.0.0-rc01"
    const val glide = "4.13.0"
    const val timePicker = "4.2.3"
    const val compose = "1.2.0-alpha07"

    const val dataStorePreferences = "1.0.0"

    const val grpc = "1.40.0"

    const val accompanistVersion = "0.17.0"

    const val extensions = "2.2.0"
    const val viewmodel = "2.4.1"
    const val activity = "1.4.0"
    const val fragment = "1.4.0"
    const val lifecycle = "2.4.0"

    const val viewBindingDelegate = "1.5.3"

    const val googleMap = "17.0.1"
    const val googleMapUtils = "2.2.3"
    const val googleMapUtilsKTX = "3.1.0"

    const val kotlinxDateTime = "0.3.2"

    const val workManagerVersion = "2.7.1"

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
    const val compile = 31
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
    const val coreKtx = "androidx.core:core-ktx:1.7.0"
}

object Google {
    const val material = "com.google.android.material:material:${Versions.material}"
    const val androidMaps = "com.google.maps.android:android-maps-utils:${Versions.googleMapUtils}"
}

object Compose {
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val animation = "androidx.compose.animation:animation:${Versions.compose}"
    const val ui = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val materialThemeAdapter =
        "com.google.android.material:compose-theme-adapter:${Versions.materialComposeThemeAdapter}"
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

object Dagger {
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val android = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val androidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
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
    const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    const val clientWebsockets = "io.ktor:ktor-client-websockets:${Versions.ktor}"
    const val clientOkhttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object TimePicker {
    const val timePicker = "com.wdullaer:materialdatetimepicker:${Versions.timePicker}"
}

object KotlinxDateTime {
    const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDateTime}"
}

object WorkManager {
    const val workRuntime = "androidx.work:work-runtime-ktx:${Versions.workManagerVersion}"
    const val workGcm = "androidx.work:work-gcm:${Versions.workManagerVersion}"
}

object DataStore {
    const val dataStorePreferences =
        "androidx.datastore:datastore-preferences:${Versions.dataStorePreferences}"
}

object Grpc {
    const val grpc = "io.grpc:grpc-okhttp:${Versions.grpc}"
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
    const val nativeDriverMacos =
        "com.squareup.sqldelight:native-driver-macosx64:${Versions.sqlDelight}"
    const val sqlliteDriver = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}"
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

/*object Compose {
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
    const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val compiler = "androidx.compose.compiler:compiler:${Versions.compose}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintCompose}"
    const val activity = "androidx.activity:activity-compose:${Versions.activityCompose}"
}*/
