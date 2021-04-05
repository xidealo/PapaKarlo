package com.bunbeauty.data.model.firebase

import com.bunbeauty.data.model.Street

data class AddressFirebase(
    var street: Street? = Street(),
    var house: String? = null,
    var flat: String? = null,
    var entrance: String? = null,
    var intercom: String? = null,
    var floor: String? = null
)
