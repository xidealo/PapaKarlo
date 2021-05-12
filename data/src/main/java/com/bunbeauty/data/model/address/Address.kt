package com.bunbeauty.data.model.address

import android.os.Parcelable
import androidx.room.Embedded
import com.bunbeauty.data.model.BaseModel
import com.bunbeauty.data.model.Street
import kotlinx.parcelize.Parcelize

@Parcelize
open class Address(
    override var uuid: String = "",
    @Embedded(prefix = "street_")
    var street: Street? = Street(),
    var house: String = "",
    var flat: String = "",
    var entrance: String = "",
    var intercom: String = "",
    var floor: String = "",
): BaseModel, Parcelable