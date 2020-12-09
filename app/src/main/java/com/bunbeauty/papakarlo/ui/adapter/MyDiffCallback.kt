package com.bunbeauty.papakarlo.ui.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.papakarlo.data.model.BaseModel

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