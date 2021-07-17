package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.presentation.view_model.base.adapter.CafeAdapterModel

class CafeDiffUtilCallback : DiffUtil.ItemCallback<CafeAdapterModel>() {

    override fun areContentsTheSame(oldItem: CafeAdapterModel, newItem: CafeAdapterModel): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: CafeAdapterModel, newItem: CafeAdapterModel): Boolean {
        return oldItem.uuid == newItem.uuid
    }
}