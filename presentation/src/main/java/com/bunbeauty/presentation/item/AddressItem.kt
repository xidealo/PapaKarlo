package com.bunbeauty.presentation.item

import com.bunbeauty.domain.model.ui.BaseItem

data class AddressItem(
    override var uuid: String,
    val address: String
): BaseItem()