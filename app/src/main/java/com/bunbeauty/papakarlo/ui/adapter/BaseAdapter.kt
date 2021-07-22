package com.bunbeauty.papakarlo.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : RecyclerView.ViewHolder, I, K : DiffUtil.Callback> :
    RecyclerView.Adapter<T>() {

    val itemList: MutableList<I> = ArrayList()
    var onItemClickListener: ((I) -> Unit)? = null

    open fun setItemList(items: List<I>, diffUtilCallback: K) {
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        itemList.clear()
        itemList.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    open fun removeItem(item: I) {
        val index = itemList.indexOf(item)
        itemList.remove(item)
        notifyItemRemoved(index)
    }

    open fun addItem(item: I) {
        itemList.add(item)
        notifyItemInserted(itemCount)
    }

    override fun getItemCount() = itemList.size
}