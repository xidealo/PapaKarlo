package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.domain.model.adapter.CartProductAdapterModel
import com.bunbeauty.domain.model.adapter.MenuProductAdapterModel
import com.bunbeauty.domain.model.local.BaseModel

class MenuProductDiffCallback : DiffUtil.ItemCallback<MenuProductAdapterModel>() {

    override fun areItemsTheSame(
        oldItem: MenuProductAdapterModel,
        newItem: MenuProductAdapterModel
    ): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(
        oldItem: MenuProductAdapterModel,
        newItem: MenuProductAdapterModel
    ): Boolean {
        return oldItem == newItem
    }
}