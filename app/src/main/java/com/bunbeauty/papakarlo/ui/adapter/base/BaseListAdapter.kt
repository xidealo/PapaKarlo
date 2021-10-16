package com.bunbeauty.papakarlo.ui.adapter.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.bunbeauty.domain.model.BaseItem

abstract class BaseListAdapter<I : BaseItem, V : ViewBinding, VH : BaseViewHolder<V, I>>(
    diffCallback: DiffUtil.ItemCallback<I>
) : ListAdapter<I, VH>(diffCallback) {

    private var onItemClickListener: ((I) -> Unit)? = null

    protected val hasItemClickListener: Boolean
        get() = onItemClickListener != null

    fun setOnItemClickListener(listener: (I) -> Unit) {
        onItemClickListener = listener
    }

    protected fun onItemClicked(item: I) {
        onItemClickListener?.invoke(item)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(getItem(position), payloads)
        }
    }

}