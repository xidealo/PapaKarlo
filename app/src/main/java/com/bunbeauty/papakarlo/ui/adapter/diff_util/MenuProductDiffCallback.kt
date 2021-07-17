package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.presentation.view_model.base.adapter.MenuProductItem

class MenuProductDiffCallback : DiffUtil.ItemCallback<MenuProductItem>() {

    override fun areItemsTheSame(
        oldItem: MenuProductItem,
        newItem: MenuProductItem
    ): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(
        oldItem: MenuProductItem,
        newItem: MenuProductItem
    ): Boolean {
        return oldItem == newItem
    }
}