package com.bunbeauty.papakarlo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.papakarlo.enums.ProductCode
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MenuProduct(
    @PrimaryKey
    override var uuid: String = "",
    var name: String = "",
    var cost: Int = 0,
    var weight: Int = 0,
    var description: String = "",
    var photoLink: String = "",
    var onFire: Boolean = false,
    var inOven: Boolean = false,
    var productCode: ProductCode = ProductCode.ALL,
    var barcode: Int = 0
) : BaseModel(), Parcelable {
    companion object {
        const val PRODUCTS: String = "products"
        const val PRODUCT_CODE: String = "product code"
    }
}