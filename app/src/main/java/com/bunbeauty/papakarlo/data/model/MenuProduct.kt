package com.bunbeauty.papakarlo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.papakarlo.enums.ProductCode
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MenuProduct(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    override var uuid: String = "",
    var name: String = "",
    var cost: Int = 0,
    var gram: Int = 0,
    var description: String = "",
    var photoLink: String = "",
    var productCode: ProductCode = ProductCode.All
) : BaseModel(), Parcelable {

    fun getStringCost() = "$cost ₽"
    fun getStringGram() = "$gram г."

    companion object {
        const val PRODUCTS: String = "products"
        const val PRODUCT: String = "product"

        const val NAME: String = "name"
        const val COST: String = "cost"
        const val GRAM: String = "gram"
        const val DESCRIPTION: String = "description"
        const val PHOTO_LINK: String = "photo link"
        const val PRODUCT_CODE: String = "product code"
    }
}