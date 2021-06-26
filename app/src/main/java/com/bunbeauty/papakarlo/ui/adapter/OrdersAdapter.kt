package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.data.enums.OrderStatus
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.papakarlo.databinding.ElementOrderBinding
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.profile.OrdersViewModel
import javax.inject.Inject

class OrdersAdapter @Inject constructor(private val iStringHelper: IStringHelper) :
    BaseAdapter<OrdersAdapter.OrderViewHolder, Order>() {

    lateinit var ordersViewModel: OrdersViewModel

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
            getBackgroundColor(
                itemList[i].orderEntity.orderStatus
            )
        )
        holder.binding?.elementOrderMvcMain?.setOnClickListener {
            onItemClickListener?.invoke(itemList[i])
        }
    }

    private fun getBackgroundColor(status: OrderStatus): Int {
        return when (status) {
            OrderStatus.NOT_ACCEPTED -> R.color.notAcceptedColor
            OrderStatus.ACCEPTED -> R.color.acceptedColor
            OrderStatus.PREPARING -> R.color.preparingColor
            OrderStatus.SENT_OUT -> R.color.sentOutColor
            OrderStatus.DONE -> R.color.doneColor
            OrderStatus.DELIVERED -> R.color.deliveredColor
            else -> R.color.notAcceptedColor
        }
    }

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementOrderBinding>(view)

    }
}