package com.bunbeauty.papakarlo.ui.adapter.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.domain.model.local.BaseModel

@Deprecated("Use  DiffUtil.ItemCallback for new adapters (refactor old)")
class MyDiffCallback(
    private val newList: List<BaseModel>,
    private val oldList: List<BaseModel>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].uuid == oldList[oldItemPosition].uuid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] == oldList[oldItemPosition]
    }
}