package com.bunbeauty.domain.model.firebase

data class MenuProductFirebase(
    var name: String = "",
    var cost: Int = 0,
    val discountCost: Int? = null,
    var weight: Int? = null,
    var description: String? = null,
    val comboDescription: String? = null,
    var photoLink: String = "",
    var productCode: String = "",
    var barcode: Int = 0,
    var visible: Boolean = false,
)
