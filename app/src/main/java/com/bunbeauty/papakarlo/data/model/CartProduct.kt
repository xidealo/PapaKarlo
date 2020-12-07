package com.bunbeauty.papakarlo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(
    override var id: Long = 0,
    override var uuid: String = "",
    var menuProduct: MenuProduct,
    var count: Int = 0,
    var fullPrice: Int = 0,
    var discount: Float = 0f
) : BaseModel(), Parcelable{
    fun getStringFullPrice() = "$fullPrice ₽"

}