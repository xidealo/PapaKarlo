package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bunbeauty.papakarlo.databinding.ElementCafeBinding
import com.bunbeauty.papakarlo.ui.adapter.base.BaseListAdapter
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.DefaultDiffCallback
import com.bunbeauty.presentation.item.CafeItem
import javax.inject.Inject

class CafeAdapter @Inject constructor() :
    BaseListAdapter<CafeItem, CafeAdapter.CafeViewHolder>(DefaultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CafeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementCafeBinding.inflate(inflater, parent, false)

        return CafeViewHolder(binding)
    }

    inner class CafeViewHolder(private val elementCafeBinding: ElementCafeBinding) :
        BaseViewHolder<CafeItem>(elementCafeBinding) {

        override fun onBind(item: CafeItem) {
            super.onBind(item)

            elementCafeBinding.run {
                elementCafeTvAddress.text = item.address
                elementCafeTvWorkTime.text = item.workingHours
                elementCafeTvTimeStatus.text = item.isOpenMessage
                elementCafeTvTimeStatus.setTextColor(item.isOpenColor)
                elementCafeMcvMain.setOnClickListener {
                    onItemClicked(item)
                }
            }
        }
    }
}