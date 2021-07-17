package com.bunbeauty.domain.model.local.order

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.bunbeauty.domain.model.local.BaseModel
import com.bunbeauty.domain.model.local.CartProduct
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(

    @Embedded
    var orderEntity: OrderEntity = OrderEntity(),

    @Relation(parentColumn = "uuid", entityColumn = "orderUuid")
    var cartProducts: List<CartProduct> = ArrayList(),

    @Ignore
    var cafeId: String = "",

    @Ignore
    var timestamp: Long? = null,
    /**
     * set UUID when get with ALSO from [OrderEntity.uuid]
     */
    @Ignore
    override var uuid: String = "",

) : BaseModel, Parcelable