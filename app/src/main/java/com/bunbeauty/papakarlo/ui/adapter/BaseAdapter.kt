package com.bunbeauty.papakarlo.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.data.model.BaseDiffUtilModel

abstract class BaseAdapter<T : RecyclerView.ViewHolder, E : BaseDiffUtilModel> : RecyclerView.Adapter<T>() {

    protected val itemList: MutableList<E> = ArrayList()

    open fun setItemList(items: List<E>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(items, itemList))
        itemList.clear()
        itemList.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    open fun removeItem(item: E) {
        val index = itemList.indexOf(item)
        itemList.remove(item)
        notifyItemRemoved(index)
    }

    open fun addItem(item: E) {
        itemList.add(item)
        notifyItemInserted(itemCount)
    }

    override fun getItemCount() = itemList.size
}