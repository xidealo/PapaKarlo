package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.domain.model.adapter.CafeAdapterModel
import com.bunbeauty.domain.model.local.cafe.Cafe

class CafeDiffUtilCallback : DiffUtil.ItemCallback<CafeAdapterModel>() {

    override fun areContentsTheSame(oldItem: CafeAdapterModel, newItem: CafeAdapterModel): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: CafeAdapterModel, newItem: CafeAdapterModel): Boolean {
        return oldItem.uuid == newItem.uuid
    }
}