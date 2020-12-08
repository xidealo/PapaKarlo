package com.bunbeauty.papakarlo.data.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    var street: String = "",
    var house: String = "",
    var flat: String = "",
    var entrance: String = "",
    var intercom: String = "",
    var floor: String = "",
    var comment: String = "",
    var phone: String = "",
    var cartProducts: ArrayList<CartProduct> = arrayListOf(),
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    override var uuid: String = "",
) : BaseModel(), Parcelable {
    companion object {
        const val ORDERS = "ORDERS"

        const val STREET = "street"
        const val HOUSE = "house"
        const val FLAT = "flat"
        const val ENTRANCE = "entrance"
        const val INTERCOM = "intercom"
        const val FLOOR = "floor"
        const val COMMENT = "comment"
        const val PHONE = "phone"
    }
}
