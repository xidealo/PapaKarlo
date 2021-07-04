package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bunbeauty.domain.model.adapter.CartProductAdapterModel
import com.bunbeauty.domain.model.adapter.MenuProductAdapterModel
import com.bunbeauty.domain.model.adapter.OrderAdapterModel
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.papakarlo.databinding.ElementOrderBinding
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.databinding.ElementCartProductBinding
import com.bunbeauty.papakarlo.ui.adapter.diff_util.CartProductDiffCallback
import com.bunbeauty.papakarlo.ui.adapter.diff_util.MyDiffCallback
import com.bunbeauty.papakarlo.ui.adapter.diff_util.OrderDiffCallback
import javax.inject.Inject

class OrdersAdapter @Inject constructor() :
    ListAdapter<OrderAdapterModel, BaseViewHolder<ViewBinding, OrderAdapterModel>>(
        OrderDiffCallback()
    ) {

    var onItemClickListener: ((OrderAdapterModel) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementOrderBinding.inflate(inflater, viewGroup, false)

        return OrderViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, OrderAdapterModel>,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, OrderAdapterModel>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(getItem(position), payloads)
        }
    }

    inner class OrderViewHolder(view: View) :
        BaseViewHolder<ElementOrderBinding, OrderAdapterModel>(DataBindingUtil.bind(view)!!) {

        override fun onBind(item: OrderAdapterModel) {
            super.onBind(item)
            with(binding) {
                elementOrderTvCode.text = item.code
                elementOrderTvDeferred.text = item.deferredTime
                elementOrderTvTime.text = item.time
                elementOrderChipStatus.text = item.orderStatus
                elementOrderChipStatus.setChipBackgroundColorResource(item.orderColor)

                elementOrderMvcMain.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }

        override fun onBind(item: OrderAdapterModel, payloads: List<Any>) {
            super.onBind(item, payloads)
            if (payloads.last() as Boolean) {
                with(binding) {
                    elementOrderChipStatus.text = item.orderStatus
                    elementOrderChipStatus.setChipBackgroundColorResource(item.orderColor)
                    elementOrderMvcMain.setOnClickListener {
                        onItemClickListener?.invoke(item)
                    }
                }
            }
        }
    }
}