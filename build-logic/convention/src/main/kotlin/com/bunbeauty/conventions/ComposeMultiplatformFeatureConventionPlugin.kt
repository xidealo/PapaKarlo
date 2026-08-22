package com.bunbeauty.conventions

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.bunbeauty.configureKotlinAndroidLibrary
import com.bunbeauty.configureKotlinCompiler
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeMultiplatformFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(receiver = target) {
            with(receiver = pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.kotlin.multiplatform.library")
                apply("org.jlleitschuh.gradle.ktlint")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                applyDefaultHierarchyTemplate()

                configureAndroidLibrary {
                    configureKotlinAndroidLibrary()
                }

                iosArm64()
                iosSimulatorArm64()

                js {
                    browser()
                }
            }
            configureKotlinCompiler()
        }
    }
}

private fun KotlinMultiplatformExtension.configureAndroidLibrary(
    configure: KotlinMultiplatformAndroidLibraryExtension.() -> Unit,
) {
    (this as org.gradle.api.plugins.ExtensionAware)
        .extensions
        .configure<KotlinMultiplatformAndroidLibraryExtension>("android", configure)
}
