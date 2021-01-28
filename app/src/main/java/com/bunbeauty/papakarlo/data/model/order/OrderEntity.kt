package com.bunbeauty.papakarlo.data.model.order

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.BaseModel
import com.bunbeauty.papakarlo.data.model.Time
import com.bunbeauty.papakarlo.enums.OrderStatus
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
@Entity
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    @Embedded(prefix = "address_")
    var address: Address = Address(),
    var comment: String = "",
    var phone: String = "",
    var time: Long = DateTime.now().millis,
    var orderStatus: OrderStatus = OrderStatus.NOT_ACCEPTED,
    var isDelivery: Boolean = true,
    var code: String = ""
) : BaseModel(), Parcelable {

    fun timeHHMM(): String {
        return Time(time, 3).toStringTimeHHMM()
    }

    fun codeString() = "Код заказа $code"

    companion object {
        const val ORDERS = "ORDERS"

        const val COMMENT = "comment"
        const val PHONE = "phone"
        const val TIMESTAMP = "timestamp"
        const val ORDER_STATUS = "order status"
    }
}
