package com.bunbeauty.domain.model.local

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class CartProduct(

    @PrimaryKey
    override var uuid: String = UUID.randomUUID().toString(),

    @Embedded(prefix = "menuProduct")
    val menuProduct: MenuProduct,

    var count: Int = 1,

    var orderUuid: String? = null

) : BaseModel, Parcelable