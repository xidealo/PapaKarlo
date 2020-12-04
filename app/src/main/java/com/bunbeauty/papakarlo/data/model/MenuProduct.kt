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
    companion object {
        const val PRODUCT_CODE: String = "product code"
        const val PRODUCTS: String = "products"
        const val PRODUCT: String = "product"
    }
}