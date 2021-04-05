package com.bunbeauty.papakarlo.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class MyDiffCallback(
    private val newList: List<com.bunbeauty.data.model.BaseDiffUtilModel>,
    private val oldList: List<com.bunbeauty.data.model.BaseDiffUtilModel>
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