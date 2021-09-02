package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.bunbeauty.papakarlo.databinding.ElementCafeBinding
import com.bunbeauty.papakarlo.ui.adapter.diff_util.DefaultDiffCallback
import com.bunbeauty.presentation.view_model.base.adapter.CafeItem
import javax.inject.Inject

class CafeAdapter @Inject constructor() :
    ListAdapter<CafeItem, CafeAdapter.CafeViewHolder>(DefaultDiffCallback()) {

    var onItemClickListener: ((CafeItem) -> Unit)? = null

    override fun onBindViewHolder(holder: CafeViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CafeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementCafeBinding.inflate(inflater, parent, false)

        return CafeViewHolder(binding.root)
    }

    inner class CafeViewHolder(view: View) :
        BaseViewHolder<ElementCafeBinding, CafeItem>(DataBindingUtil.bind(view)!!) {

        override fun onBind(item: CafeItem) {
            super.onBind(item)
            binding.run {
                elementCafeTvAddress.text = item.address
                elementCafeTvWorkTime.text = item.workingHours
                elementCafeTvTimeStatus.text = item.workingTimeMessage
                elementCafeTvTimeStatus.setTextColor(item.workingTimeMessageColor)
                elementCafeMcvMain.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }

    }
}