package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bunbeauty.papakarlo.databinding.ElementCafeBinding
import com.bunbeauty.papakarlo.ui.adapter.base.BaseListAdapter
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.DefaultDiffCallback
import com.bunbeauty.presentation.view_model.base.adapter.CafeItem
import javax.inject.Inject

class CafeAdapter @Inject constructor() :
    BaseListAdapter<CafeItem, ElementCafeBinding, CafeAdapter.CafeViewHolder>(DefaultDiffCallback()) {

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
                    onItemClicked(item)
                }
            }
        }

    }
}