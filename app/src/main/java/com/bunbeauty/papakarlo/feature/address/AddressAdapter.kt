package com.bunbeauty.papakarlo.feature.address

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bunbeauty.papakarlo.common.BaseListAdapter
import com.bunbeauty.papakarlo.common.BaseViewHolder
import com.bunbeauty.papakarlo.common.DefaultDiffCallback
import com.bunbeauty.papakarlo.databinding.ElementAddressBinding
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