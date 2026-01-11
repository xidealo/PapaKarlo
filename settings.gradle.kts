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
