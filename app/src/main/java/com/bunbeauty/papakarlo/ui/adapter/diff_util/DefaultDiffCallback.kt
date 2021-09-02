package com.bunbeauty.papakarlo.ui.adapter.diff_util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.domain.model.ui.BaseItem

open class DefaultDiffCallback<T : BaseItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}