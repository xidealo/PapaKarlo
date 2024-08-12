object Versions {
    const val gradle = "8.5.0"
    const val kotlin = "2.0.10"
    const val googleServices = "4.3.15"
    const val crashlytics = "2.9.9"
    const val navigation = "2.7.1"

    const val kotlinCoroutines = "1.9.0-RC"
    const val ktor = "2.3.3"
    const val kotlinxSerialization = "1.5.1"
    const val koin = "3.4.3"

    const val sqlDelight = "1.5.5"
    const val publisher = "3.7.0"

    const val junit = "4.13.2"
    const val testRunner = "1.3.0"
    const val appCompact = "1.7.0"
    const val coreKtx = "1.13.1"
    const val coil = "2.4.0"
    const val datetime = "0.9.0"
    const val desugar = "2.0.3"

    const val material = "1.9.0"

    const val composeBom = "2024.06.00"
    const val activityCompose = "1.7.2"

    const val dataStorePreferences = "1.0.0"

    const val activity = "1.9.1"
    const val fragment = "1.8.1"
    const val lifecycle = "2.8.4"

    const val viewBindingDelegate = "1.5.3"

    const val googleMapUtils = "2.2.3"

    const val kotlinxDateTime = "0.4.0"

    const val leakcanary = "2.12"

    const val firebase = "32.2.3"

    const val pinEntryEditText = "2.0.6"

    const val kaspresso = "1.5.3"

    const val collectionsImmutable = "0.3.7"
}

object Namespace {
    const val app = "com.bunbeauty.papakarlo"
    const val shared = "com.bunbeauty.shared"
}

object CommonApplication {
    const val versionMajor = 2
    const val versionMinor = 2
    const val versionPatch = 0

    const val versionCode = 220
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"
}

object PapaKarloApplication {
    const val applicationId = "com.bunbeuaty.papakarlo"
}

object YuliarApplication {
    const val applicationId = "com.bunbeuaty.yuliar"
}

object DjanApplication {
    const val applicationId = "com.bunbeauty.djan"
}

object GustoPubApplication {
    const val applicationId = "com.bunbeauty.gustopub"
}

object TandirHouseApplication {
    const val applicationId = "com.bunbeauty.tandirhouse"
}

object VkusKavkazaApplication {
    const val applicationId = "com.bunbeauty.vkuskavkaza"
}

object AndroidSdk {
    const val min = 24
    const val compile = 34
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
    const val publisher = "com.github.triplet.gradle:play-publisher:${Versions.publisher}"
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
    const val tripletPlay = "com.github.triplet.play"
    const val crashlytics = "com.google.firebase.crashlytics"
    const val sqldelight = "com.squareup.sqldelight"
}

object AndroidX {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompact}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
}

object Google {
    const val androidMaps = "com.google.maps.android:android-maps-utils:${Versions.googleMapUtils}"
}

object Material {
    const val material = "com.google.android.material:material:${Versions.material}"
}

object Compose {
    const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val foundation = "androidx.compose.foundation:foundation"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout"
    const val ui = "androidx.compose.ui:ui"
    const val material3 = "androidx.compose.material3:material3"
    const val uiTooling = "androidx.compose.ui:ui-tooling"
    const val uiToolingPreview =
        "androidx.compose.ui:ui-tooling-preview"
    const val uiViewbinding = "androidx.compose.ui:ui-viewbinding"
    const val activity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val lifecycle =
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycle}"
}

object Lifecycle {
    const val extensions = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
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

    const val clientOkhttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
    const val clientDarwin = "io.ktor:ktor-client-darwin:${Versions.ktor}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"
}

object MaterialDialogs {
    const val datetime = "io.github.vanpra.compose-material-dialogs:datetime:${Versions.datetime}"
}

object AndroidTools {
    const val desugar = "com.android.tools:desugar_jdk_libs:${Versions.desugar}"
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
    const val messaging = "com.google.firebase:firebase-messaging-ktx"
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
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"
}

object ViewBindingDelegate {
    const val viewBindingDelegate =
        "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBindingDelegate}"
}

object Leakcanary {
    const val android = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
}

object Kaspresso {
    const val kaspresso =
        "com.kaspersky.android-components:kaspresso:${Versions.kaspresso}"
    const val kaspressoAllureSupport =
        "com.kaspersky.android-components:kaspresso-allure-support:${Versions.kaspresso}"
    const val kaspressoComposeSupport =
        "com.kaspersky.android-components:kaspresso-compose-support:${Versions.kaspresso}"
}

object CollectionsImmutable {
    const val collectionsImmutable =
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:${Versions.collectionsImmutable}"
}
