package com.bunbeauty.domain.model.local.order

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.local.BaseModel
import com.bunbeauty.domain.model.local.address.Address
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
@Entity
data class OrderEntity(
    @PrimaryKey
    override var uuid: String = "",
    @Embedded(prefix = "address_")
    var address: Address = Address(),
    var comment: String = "",
    var phone: String = "",
    var time: Long = DateTime.now().millis,
    var orderStatus: OrderStatus = OrderStatus.NOT_ACCEPTED,
    var isDelivery: Boolean = true,
    var code: String = "",
    var email: String = "",
    var deferredTime: String = "",
    var bonus: Int = 0,
    var userId: String = ""
) : BaseModel, Parcelable
