import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.github.triplet.gradle.androidpublisher.ReleaseStatus
import com.github.triplet.gradle.play.PlayPublisherExtension
import java.io.FileInputStream
import java.util.Properties

plugins {
    kotlin(Plugin.android)
    id(Plugin.application)
    id(Plugin.kotlinAndroid)
    id(Plugin.kapt)
    id(Plugin.navigation)
    id(Plugin.googleService)
    id(Plugin.kotlinParcelize)
    id(Plugin.crashlytics)
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    id("org.jetbrains.kotlin.plugin.compose") version Versions.kotlin
    id(Plugin.tripletPlay)
}

android {
    namespace = Namespace.app

    signingConfigs {
        create("release") {
            storeFile = file(getProperty("RELEASE_STORE_FILE"))
            storePassword = getProperty("RELEASE_STORE_PASSWORD")
            keyAlias = getProperty("RELEASE_KEY_ALIAS")
            keyPassword = getProperty("RELEASE_KEY_PASSWORD")

            enableV1Signing = true
            enableV2Signing = true
        }
    }

    defaultConfig {
        minSdk = AndroidSdk.min
        compileSdk = AndroidSdk.compile
        targetSdk = AndroidSdk.target

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    flavorDimensions.add("default")
    productFlavors {
        FoodDeliveryFlavor.values().forEach { flavor ->
            create(flavor.key) {
                applicationId = flavor.applicationId
                versionCode = CommonApplication.versionCode
                versionName = CommonApplication.versionName
            }
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        abortOnError = true
        warningsAsErrors = true
        checkDependencies = true
        disable.add("VectorPath")
    }

    playConfigs {
        FoodDeliveryFlavor.values().forEach { flavor ->
            register(flavor.key) {
                commonPlayConfig(this, this@Build_gradle)
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf(
        "-Xopt-in=io.ktor.util.KtorExperimentalAPI"
    )
}

fun getProperty(key: String): String {
    val propertiesFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propertiesFile))
    val property = properties.getProperty(key)
    if (property == null) {
        println("Property with key $key not found")
    }
    return property
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":analytic"))
    implementation(project(":core"))

    implementation(AndroidX.appCompat)
    implementation(AndroidX.coreKtx)

    implementation(Material.material)

    implementation(platform(Compose.bom))
    implementation(Compose.foundation)
    implementation(Compose.foundationLayout)
    implementation(Compose.ui)
    implementation(Compose.material3)
    implementation(Compose.uiTooling)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.uiViewbinding)
    implementation(Compose.activity)
    implementation(Compose.lifecycle)

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

    implementation(ViewBindingDelegate.viewBindingDelegate)

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.test)

    implementation(Serialization.json)

    implementation(Coroutine.core)

    implementation(platform(Firebase.bom))
    implementation(Firebase.crashlyticsKtx)
    implementation(Firebase.analyticsKtx)
    implementation(Firebase.messaging)

    implementation(Coil.coil)
    implementation(Coil.coilCompose)

    implementation(MaterialDialogs.datetime)
    implementation(CollectionsImmutable.collectionsImmutable)
    coreLibraryDesugaring(AndroidTools.desugar)

    debugImplementation(Leakcanary.android)

    androidTestImplementation("com.kaspersky.android-components:kaspresso:1.5.5")
    androidTestImplementation("com.kaspersky.android-components:kaspresso-allure-support:1.5.5")
    androidTestImplementation("com.kaspersky.android-components:kaspresso-compose-support:1.5.5")

    androidTestImplementation("androidx.test:core:1.6.1")
    androidTestImplementation("androidx.test:core-ktx:1.6.1")
    androidTestImplementation("androidx.test:rules:1.6.1")
    androidTestImplementation("androidx.test:runner:1.6.2")
    androidTestImplementation("androidx.compose.ui:ui-test-manifest:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.2.1")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.3")
}

tasks.register("assembleAll") {
    dependsOn(
        FoodDeliveryFlavor.values().map { flavor ->
            flavor.assembleReleaseBundle
        }
    )
}

tasks.register("publishAll") {
    mustRunAfter("assembleAll")
    dependsOn(
        FoodDeliveryFlavor.values().map { flavor ->
            flavor.publishReleaseBundle
        }
    )
}

fun commonPlayConfig(
    playPublisherExtension: PlayPublisherExtension,
    buildGradle: Build_gradle,
) {
    with(playPublisherExtension) {
        track.set("production")
        defaultToAppBundles.set(true)
        userFraction.set(1.0)
        serviceAccountCredentials.set(buildGradle.file("google-play-api-key.json"))
        releaseStatus.set(ReleaseStatus.IN_PROGRESS)
    }
}
