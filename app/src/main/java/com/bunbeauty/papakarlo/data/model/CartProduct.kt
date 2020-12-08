package com.bunbeauty.papakarlo.data.model

import android.os.Parcelable
import android.util.Log
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class CartProduct(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    override var uuid: String = "",
    @Embedded(prefix = "menuProduct") var menuProduct: MenuProduct,
    var count: Int = 1,
    var discount: Float = 0f,
    var orderUuid: String = ""
) : BaseModel(), Parcelable {

    fun getStringFullPrice(): String {
        return getFullPrice().toString() + " ₽"
    }

    fun getFullPrice(): Int {
        return menuProduct.cost * count
    }

    companion object{
        const val CART_PRODUCT = "cart product"
        const val CART_PRODUCTS = "cart products"

        const val COUNT = "count"
        const val DISCOUNT = "discount"
    }
}