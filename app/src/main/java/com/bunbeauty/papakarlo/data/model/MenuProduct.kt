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
    var productCode: ProductCode = ProductCode.ALL
) : BaseModel(), Parcelable {

    fun getStringCost() = "$cost ₽"
    fun getStringGram() = "$weight г."

    companion object {
        const val MENU_PRODUCTS: String = "menu_products"
        const val PRODUCTS: String = "products"
        const val PRODUCT: String = "product"

        const val NAME: String = "name"
        const val COST: String = "cost"
        const val WEIGHT: String = "weight"
        const val DESCRIPTION: String = "description"
        const val PHOTO_LINK: String = "photo link"
        const val PRODUCT_CODE: String = "product code"
    }
}