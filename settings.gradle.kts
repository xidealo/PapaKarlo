pluginManagement {
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


