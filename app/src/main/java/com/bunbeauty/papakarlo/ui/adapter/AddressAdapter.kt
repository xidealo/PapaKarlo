package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bunbeauty.papakarlo.databinding.ElementAddressBinding
import com.bunbeauty.papakarlo.ui.adapter.base.BaseListAdapter
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.DefaultDiffCallback
import com.bunbeauty.presentation.item.AddressItem
import javax.inject.Inject

class AddressAdapter @Inject constructor() :
    BaseListAdapter<AddressItem, AddressAdapter.AddressViewHolder>(
        DefaultDiffCallback()
    ) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementAddressBinding.inflate(inflater, viewGroup, false)

        return AddressViewHolder(binding)
    }

    inner class AddressViewHolder(private val elementAddressBinding: ElementAddressBinding) :
        BaseViewHolder<AddressItem>(elementAddressBinding) {

        override fun onBind(item: AddressItem) {
            super.onBind(item)

            elementAddressBinding.run {
                elementAddressTvAddress.text = item.address
                if (hasItemClickListener) {
                    elementAddressMcvMain.setOnClickListener {
                        onItemClicked(item)
                    }
                }
            }
        }
    }
}