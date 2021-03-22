package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.data.model.Address
import com.bunbeauty.papakarlo.databinding.ElementAddressBinding
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.AddressesViewModel
import javax.inject.Inject

class AddressesAdapter @Inject constructor(
    private val iStringHelper: IStringHelper
) :
    BaseAdapter<AddressesAdapter.AddressViewHolder, Address>() {

    lateinit var addressesViewModel: AddressesViewModel

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementAddressBinding.inflate(inflater, viewGroup, false)

        return AddressViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, i: Int) {
        holder.binding?.address = itemList[i]
        holder.binding?.iStringHelper = iStringHelper
        holder.setOnClickListener(itemList[i])
    }

    inner class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementAddressBinding>(view)

        fun setOnClickListener(address: Address) {
            binding?.elementAddressMcvMain?.setOnClickListener {
                addressesViewModel.saveSelectedAddress(address)
            }
        }
    }
}