package com.bunbeauty.papakarlo.ui.adapter.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bunbeauty.domain.model.BaseItem

abstract class BaseViewHolder<out V : ViewBinding, I : BaseItem>(
    val binding: V
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: I

    open fun onBind(item: I) {
        this.item = item
    }

    open fun onBind(item: I, payloads: List<Any>) {
        this.item = item
    }
}