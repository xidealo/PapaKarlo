package com.bunbeauty.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.bunbeauty.AndroidSdk
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = AndroidSdk.COMPILE
                defaultConfig.targetSdk = AndroidSdk.TARGET

                defaultConfig {
                    minSdk = AndroidSdk.MIN
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }

                tasks.withType<KotlinCompile>().configureEach {
                    compilerOptions {
                        freeCompilerArgs.add("-Xstring-concat=inline")
                    }
                }
            }
        }
    }
}
