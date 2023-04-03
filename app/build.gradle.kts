import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    kotlin(Plugin.android)
    id(Plugin.application)
    id(Plugin.kotlinAndroid)
    id(Plugin.kapt)
    id(Plugin.navigation)
    id(Plugin.googleService)
    id(Plugin.kotlinParcelize)
    id(Plugin.crashlytics)
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

@Suppress("UnstableApiUsage")
android {

    signingConfigs {
        create("release") {
            storeFile = file("keystore")
            storePassword = "itisBB15092019"
            keyAlias = "papakarloKey"
            keyPassword = "Itispapakarlo08062004"
        }
    }

    defaultConfig {
        minSdk = AndroidSdk.min
        compileSdk = AndroidSdk.compile
        targetSdk = AndroidSdk.target
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
        create("papaKarlo") {
            applicationId = PapaKarloApplication.applicationId
            versionCode = CommonApplication.versionCode
            versionName = PapaKarloApplication.versionName
        }

        create("yuliar") {
            applicationId = YuliarApplication.applicationId
            versionCode = CommonApplication.versionCode
            versionName = YuliarApplication.versionName
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf(
        "-Xopt-in=io.ktor.util.KtorExperimentalAPI"
    )
}

dependencies {
    implementation(project(":shared"))

    implementation(Google.material)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.constraintLayout)

    implementation(Compose.bom)
    implementation(Compose.foundation)
    implementation(Compose.ui)
    implementation(Compose.material3)
    implementation(Compose.uiTooling)
    implementation(Compose.uiToolingPreview)
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
    implementation(Lifecycle.livedate)

    implementation(ViewBindingDelegate.viewBindingDelegate)

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.test)

    implementation(Serialization.json)

    implementation(Coroutine.core)

    implementation(platform(Firebase.bom))
    implementation(Firebase.crashlyticsKtx)
    implementation(Firebase.analyticsKtx)
    implementation(Firebase.authKtx)

    implementation(Coil.coil)
    implementation(Coil.coilCompose)

    implementation(PinEntryEditText.pinEntryEditText) {
        exclude(group = PinEntryEditText.group, module = PinEntryEditText.module)
    }

    implementation(TimePicker.timePicker)

    debugImplementation(Leakcanary.android)
}
