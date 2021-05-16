package com.bunbeauty.data.model.order

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.data.enums.OrderStatus
import com.bunbeauty.data.model.address.CafeAddress
import com.bunbeauty.data.model.BaseModel
import com.bunbeauty.data.model.address.Address
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
@Entity
data class OrderEntity(
    override var uuid: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @Embedded(prefix = "address_")
    var address: Address = Address(),
    var comment: String = "",
    var phone: String = "",
    var time: Long = DateTime.now().millis,
    var orderStatus: OrderStatus = OrderStatus.NOT_ACCEPTED,
    var isDelivery: Boolean = true,
    var code: String = "",
    var email: String = "",
    var deferred: String = ""
) : BaseModel, Parcelable
