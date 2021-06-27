package com.bunbeauty.domain.model.firebase

data class MenuProductFirebase(
    var name: String = "",
    var cost: Int = 0,
    val discountCost: Int? = null,
    var weight: Int = 0,
    var description: String = "",
    val comboDescription: String? = null,
    var photoLink: String = "",
    var onFire: Boolean = false,
    var inOven: Boolean = false,
    var productCode: String = "",
    var barcode: Int = 0,
    var visible: Boolean = true
)
