object Constants {
    const val PAPA_KARLO_FLAVOR_NAME = "papakarlo"
    const val YULIAR_FLAVOR_NAME = "yuliar"
    const val DJAN_FLAVOR_NAME = "djan"
    const val GUSTO_PUB_FLAVOR_NAME = "gustopub"
    const val TANDIR_HOUSE_FLAVOR_NAME = "tandirhouse"
    const val VKUS_KAVKAZA_FLAVOR_NAME = "vkuskavkaza"

    const val DEPLOYMENT_TARGET = "12.0"
}

fun String.getAssembleBundleRelease() =
    "assemble${this.replaceFirstChar { oldChar -> oldChar.uppercase() }}Release"

fun String.getPublishReleaseBundle() =
    "publish${this.replaceFirstChar { oldChar -> oldChar.uppercase() }}ReleaseBundle"

