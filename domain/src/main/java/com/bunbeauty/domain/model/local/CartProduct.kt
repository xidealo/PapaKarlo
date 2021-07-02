package com.bunbeauty.domain.model.local

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class CartProduct(
    @PrimaryKey
    override var uuid: String = "",
    @Embedded(prefix = "menuProduct") var menuProduct: MenuProduct = MenuProduct(),
    var count: Int = 1,
    var orderId: String? = null
) : BaseModel, Parcelable