package com.bunbeauty.papakarlo.feature.address

import com.bunbeauty.papakarlo.common.BaseItem

data class AddressItemModel(
    override val uuid: String,
    val address: String,
    val isClickable: Boolean,
) : BaseItem()