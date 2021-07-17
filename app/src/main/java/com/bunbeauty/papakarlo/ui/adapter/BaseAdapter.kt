package com.bunbeauty.papakarlo.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.domain.model.local.BaseModel

abstract class  BaseAdapter<T : RecyclerView.ViewHolder, E, K : DiffUtil.Callback>  : RecyclerView.Adapter<T>() {

    val itemList: MutableList<E> = ArrayList()
    var onItemClickListener: ((E) -> Unit)? = null

    open fun setItemList(items: List<E>, diffUtilCallback: K) {
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
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