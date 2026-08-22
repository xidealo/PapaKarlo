package com.bunbeauty.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.bunbeauty.AndroidSdk
import com.bunbeauty.setFoodDeliveryCompileSdk
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension> {
                compileSdk {
                    setFoodDeliveryCompileSdk()
                }
                defaultConfig.targetSdk = AndroidSdk.TARGET

                defaultConfig {
                    minSdk = AndroidSdk.MIN
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                    freeCompilerArgs.add("-Xstring-concat=inline")
                }
            }
        }
    }
}
