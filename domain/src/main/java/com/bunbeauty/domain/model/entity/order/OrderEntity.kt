package com.bunbeauty.domain.model.entity.order

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.ui.BaseModel
import com.bunbeauty.domain.model.ui.address.Address
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
@Entity
data class OrderEntity(
    @PrimaryKey
    override var uuid: String = "",
    var isDelivery: Boolean = true,
    var userUuid: String = "",
    var phone: String = "",
    var userAddressUuid: String? = null,
    var cafeUuid: String? = null,
    var comment: String? = null,
    var deferredTime: String? = null,
    var time: Long = DateTime.now().millis,
    var code: String = "",
    var orderStatus: OrderStatus = OrderStatus.NOT_ACCEPTED
) : BaseModel, Parcelable