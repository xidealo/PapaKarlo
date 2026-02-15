object Namespace {
    const val app = "com.bunbeauty.papakarlo"
    const val shared = "com.bunbeauty.shared"
}

object CommonApplication {
    private const val versionMajor = 3
    private const val versionMinor = 0
    private const val versionPatch = 1

    const val versionCode = 301
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val deploymentTarget = "15.5"
}

object AndroidSdk {
    const val min = 26
    const val compile = 36
    const val target = compile
}