package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.databinding.ElementAddressBinding
import javax.inject.Inject

class AddressesAdapter @Inject constructor() :
    BaseAdapter<AddressesAdapter.AddressViewHolder, Address>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementAddressBinding.inflate(inflater, viewGroup, false)

        return AddressViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, i: Int) {
        holder.binding?.address = itemList[i]
    }

    inner class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementAddressBinding>(view)
    }
}