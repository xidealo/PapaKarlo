object Versions {
    const val kotlin = "1.5.31"
    const val gradle = "7.0.2"
    const val kotlinCoroutines = "1.5.2-native-mt"
    const val ktor = "1.6.5"
    const val kotlinxSerialization = "1.3.0"
    const val koin = "3.1.2"
    const val sqlDelight = "1.5.0"

    const val slf4j = "1.7.30"
    const val constraintLayout = "2.1.1"

    const val junit = "4.13"
    const val testRunner = "1.3.0"
    const val material = "1.4.0"
    const val appCompact = "1.3.1"
    const val coil = "1.4.0"
    const val leakCanaryVersion = "2.7"
    const val dataStorePreferencesVersion = "1.0.0"

    const val analytics = "20.0.0"
    const val crashlytics = "18.2.5"
    const val firestore = "23.0.3"
    const val dynamicLinks = "20.1.1"

    const val googleServices = "4.3.10"
    const val crashlyticsClassPath = "2.8.1"

    const val navigation = "2.3.5"

    const val firebaseBom = "28.4.0"
    const val grpc = "1.40.0"

    const val accompanistVersion = "0.17.0"

    const val extensions = "2.2.0"
    const val viewmodel = "2.4.0"
    const val activity = "1.4.0"
    const val fragment = "1.3.6"
    const val lifecycle = "2.4.0"
    const val livedata = "2.4.0"

    const val googleMap = "17.0.1"
    const val googleMapUtils = "2.2.3"
    const val googleMapUtilsKTX = "3.1.0"

    const val jobaTime = "2.10.13"

    const val workManagerVersion = "2.7.1"
}

object Application {
    const val versionMajor = 1
    const val versionMinor = 1
    const val versionPatch = 3

    const val versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
    const val applicationId = "com.bunbeuaty.papakarlo"
    const val versionCode = 113
}

object AndroidSdk {
    const val min = 24
    const val compile = 31
    const val target = compile
}

object ClassPath {
    const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
    const val buildTool = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val crashlytics =
        "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsClassPath}"
    const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
    const val navigationServices =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
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
    const val nativeCocoapods = "native.cocoapods"
    const val googleService = "com.google.gms.google-services"
    const val firebasePerf = "com.google.firebase.firebase-perf"
    const val crashlytics = "com.google.firebase.crashlytics"
}

object AndroidX {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompact}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val coreKtx = "androidx.core:core-ktx:1.7.0"
}

object Google {
    const val material = "com.google.android.material:material:${Versions.material}"
    const val playServices = "com.google.android.gms:play-services-maps:${Versions.googleMap}"
    const val androidMaps = "com.google.maps.android:android-maps-utils:${Versions.googleMapUtils}"
    const val mapsUtils = "com.google.maps.android:maps-utils-ktx:${Versions.googleMapUtilsKTX}"
}

object Accompanist {
    const val navigationAnimation =
        "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanistVersion}"
}

object SquareUp {
    const val leakCanary =
        "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanaryVersion}"
}

object Test {
    const val junit = "junit:junit:${Versions.junit}"
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

object Koin {
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val test = "io.insert-koin:koin-test:${Versions.koin}"
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
    const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
}

object Navigation {
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationTesting = "androidx.navigation:navigation-testing:${Versions.navigation}"
}

object Ktor {
    const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val clientJson = "io.ktor:ktor-client-json:${Versions.ktor}"
    const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    const val clientAuth = "io.ktor:ktor-client-auth:${Versions.ktor}"
    const val clientWebsockets = "io.ktor:ktor-client-websockets:${Versions.ktor}"
    const val clientOkhttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"

    const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val clientIos = "io.ktor:ktor-client-ios:${Versions.ktor}"
    const val slf4j = "org.slf4j:slf4j-simple:${Versions.slf4j}"
}

object Coil {
    const val coilAccompanist = "io.coil-kt:coil:${Versions.coil}"
}

object JobaTime {
    const val jobaTime = "joda-time:joda-time:${Versions.jobaTime}"
}

object WorkManager {
    const val workRuntime = "androidx.work:work-runtime-ktx:${Versions.workManagerVersion}"
    const val workGcm = "androidx.work:work-gcm:${Versions.workManagerVersion}"
}

object DataStore {
    const val dataStorePreferences =
        "androidx.datastore:datastore-preferences:${Versions.dataStorePreferencesVersion}"
}

object Grpc {
    const val grpc = "io.grpc:grpc-okhttp:${Versions.grpc}"
}

object Serialization {
    const val json =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
}

object Firebase {
    const val analytics = "com.google.firebase:firebase-analytics:${Versions.analytics}"
    const val perf = "com.google.firebase:firebase-perf:${Versions.analytics}"
    const val crashlytics = "com.google.firebase:firebase-crashlytics:${Versions.crashlytics}"
    const val firestore = "com.google.firebase:firebase-firestore-ktx:${Versions.firestore}"
    const val dynamicLink =
        "com.google.firebase:firebase-dynamic-links-ktx:${Versions.dynamicLinks}"
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
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
    const val coroutineCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
}

object Lifecycle {
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.extensions}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewmodel}"
    const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedata}"
}
