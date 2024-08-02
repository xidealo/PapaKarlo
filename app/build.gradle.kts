import Constants.DJAN_FLAVOR_NAME
import Constants.GUSTO_PUB_FLAVOR_NAME
import Constants.PAPA_KARLO_FLAVOR_NAME
import Constants.TANDIR_HOUSE_FLAVOR_NAME
import Constants.VKUS_KAVKAZA_FLAVOR_NAME
import Constants.YULIAR_FLAVOR_NAME
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
        create(PAPA_KARLO_FLAVOR_NAME) {
            applicationId = PapaKarloApplication.applicationId
            versionCode = CommonApplication.versionCode
            versionName = CommonApplication.versionName
        }
        create(YULIAR_FLAVOR_NAME) {
            applicationId = YuliarApplication.applicationId
            versionCode = CommonApplication.versionCode
            versionName = CommonApplication.versionName
        }
        create(DJAN_FLAVOR_NAME) {
            applicationId = DjanApplication.applicationId
            versionCode = CommonApplication.versionCode
            versionName = CommonApplication.versionName
        }
        create(GUSTO_PUB_FLAVOR_NAME) {
            applicationId = GustoPubApplication.applicationId
            versionCode = CommonApplication.versionCode
            versionName = CommonApplication.versionName
        }
        create(TANDIR_HOUSE_FLAVOR_NAME) {
            applicationId = TandirHouseApplication.applicationId
            versionCode = CommonApplication.versionCode
            versionName = CommonApplication.versionName
        }
        create(VKUS_KAVKAZA_FLAVOR_NAME) {
            applicationId = VkusKavkazaApplication.applicationId
            versionCode = CommonApplication.versionCode
            versionName = CommonApplication.versionName
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
        register(PAPA_KARLO_FLAVOR_NAME) {
            commonPlayConfig(this, this@Build_gradle)
        }
        register(YULIAR_FLAVOR_NAME) {
            commonPlayConfig(this, this@Build_gradle)
        }
        register(DJAN_FLAVOR_NAME) {
            commonPlayConfig(this, this@Build_gradle)
        }
        register(GUSTO_PUB_FLAVOR_NAME) {
            commonPlayConfig(this, this@Build_gradle)
        }
        register(TANDIR_HOUSE_FLAVOR_NAME) {
            commonPlayConfig(this, this@Build_gradle)
        }
        register(VKUS_KAVKAZA_FLAVOR_NAME) {
            commonPlayConfig(this, this@Build_gradle)
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
    coreLibraryDesugaring(AndroidTools.desugar)

    debugImplementation(Leakcanary.android)

    androidTestImplementation(Kaspresso.kaspresso)
    androidTestImplementation(Kaspresso.kaspressoAllureSupport)
    androidTestImplementation(Kaspresso.kaspressoComposeSupport)

    implementation(CollectionsImmutable.collectionsImmutable)
}

tasks.register("assembleAll") {
    dependsOn(
        PAPA_KARLO_FLAVOR_NAME.getAssembleBundleRelease(),
        YULIAR_FLAVOR_NAME.getAssembleBundleRelease(),
        DJAN_FLAVOR_NAME.getAssembleBundleRelease(),
        GUSTO_PUB_FLAVOR_NAME.getAssembleBundleRelease(),
        TANDIR_HOUSE_FLAVOR_NAME.getAssembleBundleRelease(),
        VKUS_KAVKAZA_FLAVOR_NAME.getAssembleBundleRelease(),
    )
}
//- name: start publish
//        run: ./gradlew publishAll
tasks.register("publishAll") {
    mustRunAfter("assembleAll")
    dependsOn(PAPA_KARLO_FLAVOR_NAME.getPublishReleaseBundle())
    dependsOn(YULIAR_FLAVOR_NAME.getPublishReleaseBundle())
    dependsOn(DJAN_FLAVOR_NAME.getPublishReleaseBundle())
    dependsOn(GUSTO_PUB_FLAVOR_NAME.getPublishReleaseBundle())
    dependsOn(TANDIR_HOUSE_FLAVOR_NAME.getPublishReleaseBundle())
    dependsOn(VKUS_KAVKAZA_FLAVOR_NAME.getPublishReleaseBundle())
}

fun commonPlayConfig(
    playPublisherExtension: PlayPublisherExtension,
    buildGradle: Build_gradle,
) {
    playPublisherExtension.track.set("production")
    playPublisherExtension.defaultToAppBundles.set(true)
    playPublisherExtension.serviceAccountCredentials.set(buildGradle.file("google-play-api-key.json"))
    playPublisherExtension.releaseStatus.set(ReleaseStatus.DRAFT)
}
