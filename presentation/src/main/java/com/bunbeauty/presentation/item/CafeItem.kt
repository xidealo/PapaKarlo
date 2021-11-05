package com.bunbeauty.presentation.item

import com.bunbeauty.domain.model.BaseItem

data class CafeItem(
    override var uuid: String,
    val address: String,
    val workingHours: String,
    val workingTimeMessage: String,
    val isOpen: Boolean,
) : BaseItem()