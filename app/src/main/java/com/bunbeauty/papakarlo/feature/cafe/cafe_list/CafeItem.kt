package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import com.bunbeauty.papakarlo.common.BaseItem

data class CafeItem(
    override var uuid: String,
    val address: String,
    val workingHours: String,
    val isOpenMessage: String,
    val isOpenColor: Int,
) : BaseItem()