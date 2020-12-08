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
}