pluginManagement {
    includeBuild("build-logic")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// AGP SDK loader (RepoManager) calls kotlinx.coroutines.runBlocking during IDE import.
// KGP / Compose / included builds can put a different kotlinx-coroutines jar on the
// plugin classpath, which yields:
//   NoSuchMethodError: AbstractTimeSourceKt.access$getTimeSource$p()
// Keep a single version on every project's buildscript classpath. App runtime can
// still use kotlinCoroutines from the version catalog (currently 1.11.0).
val pluginClasspathCoroutines = "1.8.1"

gradle.beforeProject {
    buildscript {
        repositories {
            google()
            mavenCentral()
            gradlePluginPortal()
        }
        configurations.classpath {
            resolutionStrategy {
                eachDependency {
                    if (requested.group == "org.jetbrains.kotlinx" &&
                        requested.name.startsWith("kotlinx-coroutines")
                    ) {
                        useVersion(pluginClasspathCoroutines)
                        because(
                            "Single kotlinx-coroutines version on the plugin classpath " +
                                "for AGP SDK loader during IDE sync"
                        )
                    }
                }
            }
        }
    }
}

include(
    ":app",
    ":shared",
    ":webApp",
)
rootProject.name = "PapaKarlo"
include(":analytic")
include(":core")
include(":di")
include(":designsystem")
include(":feature:menu")
include(":feature:profile")
include(":feature:productdetails")
include(":feature:auth")
include(":feature:address")
include(":feature:splash")
include(":feature:cafe")
include(":feature:update")
include(":feature:order")
include(":feature:createorder")
include(":feature:consumercart")
