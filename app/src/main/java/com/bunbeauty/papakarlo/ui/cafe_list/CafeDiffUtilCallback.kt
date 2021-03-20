package com.bunbeauty.papakarlo.ui.cafe_list

import androidx.recyclerview.widget.DiffUtil
import com.bunbeauty.data.model.cafe.Cafe

class CafeDiffUtilCallback : DiffUtil.ItemCallback<Cafe>() {

    override fun areContentsTheSame(oldItem: Cafe, newItem: Cafe): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Cafe, newItem: Cafe): Boolean {
        return oldItem.cafeEntity.id == newItem.cafeEntity.id
    }
}