package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.presentation.view_model.base.adapter.MenuProductAdapterModel

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