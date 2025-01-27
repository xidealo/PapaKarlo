import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.github.triplet.gradle.androidpublisher.ReleaseStatus
import com.github.triplet.gradle.play.PlayPublisherExtension
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation)
    alias(libs.plugins.google.service)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.ktLint)
    alias(libs.plugins.triplet.play)
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
        disable.add("GradleDependency")
        disable.add("AndroidGradlePluginVersion")
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

    implementation(libs.appcompat)
    implementation(libs.core.ktx)
    implementation(libs.material)

    implementation(libs.bundles.navigation)
    androidTestImplementation(libs.navigation.testing)

    implementation(libs.bundles.lifecycle)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)

    implementation(libs.viewbindingpropertydelegate)
    implementation(libs.bundles.di)
    testImplementation(libs.koin.test)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.bundles.coil)
    implementation(libs.material.dialogs.datetime)
    implementation(libs.kotlinx.collections.immutable)

    debugImplementation(libs.leakcanary.android)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    androidTestImplementation(libs.bundles.kaspresso)

    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.activity.compose)
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
    buildGradle: Build_gradle
) {
    with(playPublisherExtension) {
        track.set("production")
        defaultToAppBundles.set(true)
        userFraction.set(0.99)
        serviceAccountCredentials.set(buildGradle.file("google-play-api-key.json"))
        releaseStatus.set(ReleaseStatus.IN_PROGRESS)
    }
}
