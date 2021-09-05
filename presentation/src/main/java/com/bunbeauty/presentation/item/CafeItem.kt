package com.bunbeauty.presentation.item

import com.bunbeauty.domain.model.ui.BaseItem

data class CafeItem(
    override var uuid: String,
    val address: String,
    val workingHours: String,
    val workingTimeMessage: String,
    val workingTimeMessageColor: Int
) : BaseItem()