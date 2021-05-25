package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.papakarlo.databinding.ElementOrderBinding
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.profile.OrdersViewModel
import javax.inject.Inject

class OrdersAdapter @Inject constructor(
    private val stringHelper: IStringHelper
) : BaseAdapter<OrdersAdapter.OrderViewHolder, Order>() {

    lateinit var ordersViewModel: OrdersViewModel

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementOrderBinding.inflate(inflater, viewGroup, false)

        return OrderViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, i: Int) {
        holder.setListener(itemList[i])
        holder.binding?.order = itemList[i]
        holder.binding?.stringHelper = stringHelper
    }

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementOrderBinding>(view)

        fun setListener(order: Order) {
            binding?.elementOrderMcvOrder?.setOnClickListener {
                ordersViewModel.onOrderClicked(order)
            }
        }
    }
}