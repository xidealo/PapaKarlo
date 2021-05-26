package com.bunbeauty.papakarlo.ui.cafe_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.bunbeauty.domain.model.adapter.CafeAdapterModel
import com.bunbeauty.papakarlo.databinding.ElementCafeBinding
import com.bunbeauty.papakarlo.presentation.cafe.CafeListViewModel
import com.bunbeauty.papakarlo.ui.adapter.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.CafeDiffUtilCallback
import javax.inject.Inject

class CafeAdapter @Inject constructor() :
    ListAdapter<CafeAdapterModel, BaseViewHolder<ViewBinding, CafeAdapterModel>>(
        CafeDiffUtilCallback()
    ) {

    lateinit var cafeListViewModel: CafeListViewModel
    var onItemClickListener: ((CafeAdapterModel) -> Unit)? = null

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, CafeAdapterModel>,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CafeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementCafeBinding.inflate(inflater, parent, false)

        return CafeViewHolder(binding.root)
    }

    inner class CafeViewHolder(view: View) :
        BaseViewHolder<ElementCafeBinding, CafeAdapterModel>(DataBindingUtil.bind(view)!!) {

        override fun onBind(item: CafeAdapterModel) {
            super.onBind(item)
            with(binding) {
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