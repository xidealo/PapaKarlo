package com.bunbeauty.papakarlo.data.model.fb_model

import com.bunbeauty.papakarlo.enums.ProductCode

data class MenuProductFB(
    var name: String = "",
    var cost: Int = 1000,
    var weight: Int = 0,
    var description: String = "",
    var photoLink: String = "",
    var onFire: Boolean = false,
    var inOven: Boolean = false,
    var productCode: ProductCode = ProductCode.ALL,
    var barcode: Int = 0
) {
}