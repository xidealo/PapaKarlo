package com.bunbeauty.papakarlo.common

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

open class DefaultDiffCallback<T : BaseItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}