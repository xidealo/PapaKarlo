package com.example.domain_firebase.model.firebase

data class MenuProductFirebase(
    var name: String = "",
    var cost: Int = 0,
    val discountCost: Int? = null,
    var weight: Int? = null,
    var description: String = "",
    val comboDescription: String? = null,
    var photoLink: String = "",
    var productCode: String = "",
    var barcode: Int? = null,
    var visible: Boolean = false,
)