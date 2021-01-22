package com.bunbeauty.papakarlo.data.model.order

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.BaseModel
import com.bunbeauty.papakarlo.data.model.Time
import com.bunbeauty.papakarlo.enums.OrderStatus
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
@Entity
data class Order(
    @Embedded(prefix = "address_")
    var address: Address = Address(),
    var comment: String = "",
    var phone: String = "",
    var time: Long = DateTime.now().millis,
    var orderStatus: OrderStatus = OrderStatus.NOT_ACCEPTED,
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    override var uuid: String = "",
) : BaseModel(), Parcelable {

    fun getTimeHHMM(): String {
        val newTime = Time(time, 3)
        return newTime.toStringTimeHHMM()
    }

    fun getOrderData() =
        "Доставка на улицу:${address.street}\n" +
                "Дом:${address.house} " +
                "Квартира:${address.flat} " +
                "Подъезд:${address.entrance} " +
                "Домофон:${address.intercom} " +
                "Этаж:${address.floor}\n" +
                "Комментарий:$comment\n" +
                "Контактный телефон:$phone"

    fun getUuidCode() = "Код заказа $uuid"

    companion object {
        const val ORDERS = "ORDERS"

        const val COMMENT = "comment"
        const val PHONE = "phone"
        const val TIMESTAMP = "timestamp"
        const val ORDER_STATUS = "order status"
    }
}
