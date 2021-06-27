package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.domain.model.order.Order
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.papakarlo.databinding.ElementOrderBinding
import com.bunbeauty.domain.util.string_helper.IStringHelper
import javax.inject.Inject

class OrdersAdapter @Inject constructor(
    private val iStringHelper: IStringHelper,
    private val orderUtil: IOrderUtil
) :
    BaseAdapter<OrdersAdapter.OrderViewHolder, Order>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementOrderBinding.inflate(inflater, viewGroup, false)

        return OrderViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, i: Int) {
        holder.binding?.elementOrderTvCode?.text = itemList[i].orderEntity.code
        holder.binding?.elementOrderTvDeferred?.text =
            if (itemList[i].orderEntity.deferredTime.isNotEmpty())
                "Ко времени: ${itemList[i].orderEntity.deferredTime}"
            else
                ""

        holder.binding?.elementOrderTvTime?.text =
            iStringHelper.toStringTime(itemList[i].orderEntity)
        holder.binding?.elementOrderChipStatus?.text =
            iStringHelper.toStringOrderStatus(itemList[i].orderEntity.orderStatus)
        holder.binding?.elementOrderChipStatus?.setChipBackgroundColorResource(
            orderUtil.getBackgroundColor(
                itemList[i].orderEntity.orderStatus
            )
        )
        holder.binding?.elementOrderMvcMain?.setOnClickListener {
            onItemClickListener?.invoke(itemList[i])
        }
    }

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementOrderBinding>(view)
    }
}