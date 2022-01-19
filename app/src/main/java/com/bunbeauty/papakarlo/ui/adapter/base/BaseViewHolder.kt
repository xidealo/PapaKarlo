package com.bunbeauty.papakarlo.ui.adapter.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bunbeauty.domain.model.BaseItem

abstract class BaseViewHolder<I : BaseItem>(viewBinding: ViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    lateinit var item: I

    open fun onBind(item: I) {
        this.item = item
    }

    open fun onBind(item: I, payloads: List<Any>) {
        this.item = item
    }
}