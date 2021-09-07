package com.bunbeauty.presentation.item

import com.bunbeauty.domain.model.BaseItem

data class AddressItem(
    override var uuid: String,
    val address: String
): BaseItem()