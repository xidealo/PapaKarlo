plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "papakarlo-web.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(project(":core"))
                implementation(project(":designsystem"))
                implementation(project(":analytic"))

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)

                implementation(libs.bundles.di)
                implementation(libs.bundles.coil)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.sqlDelight.sqljs)
                implementation(npm("sql.js", "1.6.2"))
                implementation(npm("os-browserify", "0.3.0"))
                implementation(npm("path-browserify", "1.0.1"))
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            }
        }
    }
}
