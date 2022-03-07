package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bunbeauty.papakarlo.common.BaseListAdapter
import com.bunbeauty.papakarlo.common.BaseViewHolder
import com.bunbeauty.papakarlo.common.DefaultDiffCallback
import com.bunbeauty.papakarlo.databinding.ElementCafeBinding

class CafeAdapter :
    BaseListAdapter<CafeItemModel, CafeAdapter.CafeViewHolder>(DefaultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CafeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementCafeBinding.inflate(inflater, parent, false)

        return CafeViewHolder(binding)
    }

    inner class CafeViewHolder(private val elementCafeBinding: ElementCafeBinding) :
        BaseViewHolder<CafeItemModel>(elementCafeBinding) {

        override fun onBind(item: CafeItemModel) {
            super.onBind(item)

            elementCafeBinding.run {
                elementCafeTvAddress.text = item.address
                elementCafeTvWorkTime.text = item.workingHours
                setIsOpenMessage()
            }
        }

        override fun onBind(item: CafeItemModel, payloads: List<Any>) {
            super.onBind(item, payloads)

            if (payloads.last() as Boolean) {
                setIsOpenMessage()
            }
        }

        private fun setIsOpenMessage() {
            elementCafeBinding.run {
                elementCafeTvTimeStatus.text = item.isOpenMessage
                //elementCafeTvTimeStatus.setTextColor(item.isOpenColor)
                elementCafeMcvMain.setOnClickListener {
                    onItemClicked(item)
                }
            }
        }
    }
}