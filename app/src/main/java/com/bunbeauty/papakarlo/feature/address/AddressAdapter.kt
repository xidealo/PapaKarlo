package com.bunbeauty.papakarlo.feature.address

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bunbeauty.papakarlo.common.BaseListAdapter
import com.bunbeauty.papakarlo.common.BaseViewHolder
import com.bunbeauty.papakarlo.common.DefaultDiffCallback
import com.bunbeauty.papakarlo.databinding.ElementAddressBinding

class AddressAdapter:
    BaseListAdapter<AddressItemModel, AddressAdapter.AddressViewHolder>(
        DefaultDiffCallback()
    ) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementAddressBinding.inflate(inflater, viewGroup, false)

        return AddressViewHolder(binding)
    }

    inner class AddressViewHolder(private val elementAddressBinding: ElementAddressBinding) :
        BaseViewHolder<AddressItemModel>(elementAddressBinding) {

        override fun onBind(itemModel: AddressItemModel) {
            super.onBind(itemModel)

            elementAddressBinding.run {
                elementAddressTvAddress.text = itemModel.address
                if (hasItemClickListener) {
                    elementAddressMcvMain.setOnClickListener {
                        onItemClicked(itemModel)
                    }
                }
            }
        }
    }
}