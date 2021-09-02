package com.bunbeauty.presentation.view_model.base.adapter

import com.bunbeauty.domain.model.ui.BaseItem

data class AddressItem(
    override var uuid: String,
    val address: String
): BaseItem()