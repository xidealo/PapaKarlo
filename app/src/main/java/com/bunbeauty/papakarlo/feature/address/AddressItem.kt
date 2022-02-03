package com.bunbeauty.papakarlo.feature.address

import com.bunbeauty.papakarlo.common.BaseItem

data class AddressItem(
    override val uuid: String,
    val address: String
): BaseItem()