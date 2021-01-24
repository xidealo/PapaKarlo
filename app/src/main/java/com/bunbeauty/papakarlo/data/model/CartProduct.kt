package com.bunbeauty.papakarlo.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class CartProduct(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    @Embedded(prefix = "menuProduct") var menuProduct: MenuProduct = MenuProduct(),
    var count: Int = 1,
    var discount: Float = 0f,
    var orderId: Long? = null
) : BaseModel(), Parcelable {

    fun stringFullPrice(): String {
        return fullPrice().toString() + " ₽"
    }

    fun fullPrice(): Int {
        return menuProduct.cost * count
    }

    companion object {
        const val CART_PRODUCTS = "cart products"

        const val COUNT = "count"
        const val DISCOUNT = "discount"
    }
}