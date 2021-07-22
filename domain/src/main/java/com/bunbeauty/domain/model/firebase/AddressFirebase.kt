package com.bunbeauty.domain.model.firebase

import com.bunbeauty.domain.model.ui.Street

data class AddressFirebase(
    var street: Street? = Street(),
    var house: String? = null,
    var flat: String? = null,
    var entrance: String? = null,
    var comment: String? = null,
    var floor: String? = null
)
