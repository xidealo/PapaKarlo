package com.bunbeauty.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class CartProduct(
    override var uuid: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @Embedded(prefix = "menuProduct") var menuProduct: MenuProduct = MenuProduct(),
    var count: Int = 1,
    var orderId: String? = null
) : BaseModel, Parcelable