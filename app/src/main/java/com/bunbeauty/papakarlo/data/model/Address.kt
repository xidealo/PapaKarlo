package com.bunbeauty.papakarlo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Address(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    var street: String = "",
    var house: String = "",
    var flat: String = "",
    var entrance: String = "",
    var intercom: String = "",
    var floor: String = "",
    var city: String = ""
) : BaseModel(), Parcelable {

    fun addressString() = checkLastSymbol(
        "Улица:${street}, " +
                "Дом:${house}, " +
                flatString() +
                entranceString() +
                intercomString() +
                floorString()
    )

    fun checkLastSymbol(data: String): String {
        if (data.trim().last() == ',')
            return data.substringBeforeLast(",")

        return data
    }

    fun flatString(): String {
        return if (flat.isNotEmpty())
            "Квартира:${flat}, "
        else
            ""
    }

    fun entranceString(): String {
        return if (entrance.isNotEmpty())
            "Подъезд:${entrance}, "
        else
            ""
    }

    fun intercomString(): String {
        return if (intercom.isNotEmpty())
            "Домофон:${intercom}, "
        else
            ""
    }

    fun floorString(): String {
        return if (floor.isNotEmpty())
            "Этаж:${floor}"
        else
            ""
    }

    companion object {
        const val STREET = "street"
        const val HOUSE = "house"
        const val FLAT = "flat"
        const val ENTRANCE = "entrance"
        const val INTERCOM = "intercom"
        const val FLOOR = "floor"
    }
}
