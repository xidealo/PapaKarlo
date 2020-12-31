package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.data.model.order.OrderWithCartProducts
import com.bunbeauty.papakarlo.databinding.ElementOrderBinding
import javax.inject.Inject

class OrdersAdapter @Inject constructor() :
    BaseAdapter<OrdersAdapter.OrderViewHolder, OrderWithCartProducts>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementOrderBinding.inflate(inflater, viewGroup, false)

        return OrderViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, i: Int) {
        holder.binding?.menuProduct = itemList[i]
    }

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementOrderBinding>(view)
    }
}