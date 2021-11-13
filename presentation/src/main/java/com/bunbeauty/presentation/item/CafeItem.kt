package com.bunbeauty.presentation.item

import com.bunbeauty.domain.model.BaseItem

data class CafeItem(
    override var uuid: String,
    val address: String,
    val workingHours: String,
    val isOpenMessage: String,
    val isOpenColor: Int,
) : BaseItem()