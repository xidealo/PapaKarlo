package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.presentation.view_model.base.adapter.CafeItem

class CafeDiffUtilCallback : DiffUtil.ItemCallback<CafeItem>() {

    override fun areContentsTheSame(oldItem: CafeItem, newItem: CafeItem): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: CafeItem, newItem: CafeItem): Boolean {
        return oldItem.uuid == newItem.uuid
    }
}