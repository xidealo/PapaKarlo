package com.bunbeauty.papakarlo.feature.address

import com.bunbeauty.papakarlo.common.BaseItem

data class AddressItem(
    override var uuid: String,
    val address: String
): BaseItem()