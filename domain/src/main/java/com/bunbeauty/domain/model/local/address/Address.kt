package com.bunbeauty.domain.model.local.address

import android.os.Parcelable
import androidx.room.Embedded
import com.bunbeauty.domain.model.local.BaseModel
import com.bunbeauty.domain.model.local.Street
import kotlinx.parcelize.Parcelize

@Parcelize
open class Address(
    override var uuid: String = "",
    @Embedded(prefix = "street_")
    var street: Street? = Street(),
    var house: String = "",
    var flat: String = "",
    var entrance: String = "",
    var comment: String = "",
    var floor: String = "",
): BaseModel, Parcelable