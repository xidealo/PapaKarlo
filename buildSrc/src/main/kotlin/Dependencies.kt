object Namespace {
    const val app = "com.bunbeauty.papakarlo"
    const val shared = "com.bunbeauty.shared"
}

object CommonApplication {
    private const val versionMajor = 2
    private const val versionMinor = 8
    private const val versionPatch = 7

    const val versionCode = 287
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val deploymentTarget = "12.0"
}

object AndroidSdk {
    const val min = 26
    const val compile = 36
    const val target = compile
}