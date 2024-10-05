enum class FoodDeliveryFlavor(
    val key: String,
    val applicationId: String,
) {
    PAPA_KARLO(
        key = "papakarlo",
        applicationId = "com.bunbeuaty.papakarlo"
    ),
    YULIAR(
        key = "yuliar",
        applicationId = "com.bunbeuaty.yuliar"
    ),
    DJAN(
        key = "djan",
        applicationId = "com.bunbeauty.djan"
    ),
    GUSTO_PUB(
        key = "gustopub",
        applicationId = "com.bunbeauty.gustopub"
    ),
    TANDIR_HOUSE(
        key = "tandirhouse",
        applicationId = "com.bunbeauty.tandirhouse"
    ),
    VKUS_KAVKAZA(
        key = "vkuskavkaza",
        applicationId = "com.bunbeauty.vkuskavkaza"
    ),
    ANTALYA_KABAB(
        key = "antalyakebab",
        applicationId = "com.bunbeauty.antalyakebab"
    ),
    LEGENDA(
        key = "legenda",
        applicationId = "com.bunbeauty.legenda"
    ),
    USADBA(
        key = "usadba",
        applicationId = "com.bunbeauty.usadba"
    );

    val assembleReleaseBundle: String
        get() {
            return "assemble${key.replaceFirstChar { oldChar -> oldChar.uppercase() }}ReleaseBundle"
        }

    val publishReleaseBundle: String
        get() {
            return "publish${key.replaceFirstChar { oldChar -> oldChar.uppercase() }}ReleaseBundle"
        }

    val bootstrapReleaseBundle: String
        get() {
            return "bootstrap${key.replaceFirstChar { oldChar -> oldChar.uppercase() }}ReleaseBundle"
        }
}