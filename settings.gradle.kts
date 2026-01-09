pluginManagement {
    includeBuild("build-logic")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include(
    ":app",
    ":shared",
)
rootProject.name = "PapaKarlo"
include(":analytic")
include(":core")
include(":di")
include(":designsystem")
include(":feature:menu")
