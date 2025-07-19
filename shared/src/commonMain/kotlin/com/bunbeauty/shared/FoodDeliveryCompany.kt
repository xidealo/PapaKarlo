package com.bunbeauty.shared

import com.bunbeauty.shared.domain.exeptions.UnknownCompanyException

enum class FoodDeliveryCompany(
    val flavor: String,
    val companyUuid: String
) {
    PAPA_KARLO(
        flavor = "papakarlo",
        companyUuid = "7416dba5-2825-4fe3-abfb-1494a5e2bf99"
    ),
    YULIAR(
        flavor = "yuliar",
        companyUuid = "8b91126f-be08-423a-b1dc-dea78ae79cd0"
    ),
    DJAN(
        flavor = "djan",
        companyUuid = "136ce426-15ab-49eb-ab78-19da43fca191"
    ),
    GUSTO_PUB(
        flavor = "gustopub",
        companyUuid = "e1d1474b-6fba-4dff-826f-48e89abc48e3"
    ),
    TANDIR_HOUSE(
        flavor = "tandirhouse",
        companyUuid = "355b609e-12af-4622-8f40-a42ea0eef85a"
    ),
    VKUS_KAVKAZA(
        flavor = "vkuskavkaza",
        companyUuid = "90aa09b7-5407-435b-82eb-6d450660e405"
    ),
    EST_POEST(
        flavor = "estpoest",
        companyUuid = "a7a7dd46-bad2-4736-b923-86474f693664"
    ),
    LEGENDA(
        flavor = "legenda",
        companyUuid = "05a0b970-501e-47a5-8988-ffe18ee61bb1"
    ),
    USADBA(
        flavor = "usadba",
        companyUuid = "27c34bd1-9620-452a-95e1-b51638df01d4"
    ),
    EMOJI(
        flavor = "emoji",
        companyUuid = "0b7b2388-0992-48cc-812e-79bb44ff58ba"
    ),
    LIMONAD(
        flavor = "limonad",
        companyUuid = "405bca15-13f5-4f1b-9215-663d230e9bdf"
    ),
    TAVERNA(
        flavor = "taverna",
        companyUuid = "02046f6d-40f2-4b24-bbea-a742079fc66e"
    );

    companion object {

        fun getByFlavor(flavor: String): FoodDeliveryCompany {
            return FoodDeliveryCompany.entries.find { company ->
                company.flavor == flavor
            } ?: throw UnknownCompanyException(flavor = flavor)
        }
    }
}
