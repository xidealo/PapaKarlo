package com.bunbeauty.domain.model.order

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.bunbeauty.domain.model.BaseModel
import com.bunbeauty.domain.model.CartProduct
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(

    @Embedded
    var orderEntity: OrderEntity = OrderEntity(),

    @Relation(parentColumn = "uuid", entityColumn = "orderId")
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