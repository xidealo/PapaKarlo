package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.domain.model.local.address.Address
import com.bunbeauty.papakarlo.databinding.ElementAddressBinding
import com.bunbeauty.domain.util.string_helper.IStringHelper
import javax.inject.Inject

class AddressesAdapter @Inject constructor(
    private val iStringHelper: IStringHelper
) : BaseAdapter<AddressesAdapter.AddressViewHolder, Address, MyDiffCallback>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementAddressBinding.inflate(inflater, viewGroup, false)

        return AddressViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, i: Int) {
        holder.binding?.address = itemList[i]
        holder.binding?.iStringHelper = iStringHelper
        holder.binding?.elementAddressMcvMain?.setOnClickListener{
            onItemClickListener?.invoke(itemList[i])
        }
    }


    inner class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementAddressBinding>(view)
    }
}