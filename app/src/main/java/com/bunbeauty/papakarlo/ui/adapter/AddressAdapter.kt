package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.bunbeauty.papakarlo.databinding.ElementAddressBinding
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.DefaultDiffCallback
import com.bunbeauty.presentation.item.AddressItem
import javax.inject.Inject

class AddressAdapter @Inject constructor() :
    ListAdapter<AddressItem, AddressAdapter.AddressViewHolder>(DefaultDiffCallback()) {

    private var onItemClickListener: ((AddressItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (AddressItem) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementAddressBinding.inflate(inflater, viewGroup, false)

        return AddressViewHolder(binding.root)
    }

    inner class AddressViewHolder(view: View) :
        BaseViewHolder<ElementAddressBinding, AddressItem>(DataBindingUtil.bind(view)!!) {

        override fun onBind(item: AddressItem) {
            super.onBind(item)

            binding.run {
                elementAddressTvAddress.text = item.address
                elementAddressMcvMain.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }
}