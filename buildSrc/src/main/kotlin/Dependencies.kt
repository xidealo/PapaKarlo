object Namespace {
    const val app = "com.bunbeauty.papakarlo"
    const val shared = "com.bunbeauty.shared"
}

object CommonApplication {
    private const val versionMajor = 2
    private const val versionMinor = 3
    private const val versionPatch = 1

    const val versionCode = 231
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val deploymentTarget = "12.0"
}

object AndroidSdk {
    const val min = 24
    const val compile = 34
    const val target = compile
}