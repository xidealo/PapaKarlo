package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bunbeauty.papakarlo.databinding.ElementCategoryBinding
import com.bunbeauty.papakarlo.ui.adapter.base.BaseListAdapter
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.DefaultDiffCallback
import com.bunbeauty.presentation.item.CategoryItem
import javax.inject.Inject

class CategoryAdapter @Inject constructor() :
    BaseListAdapter<CategoryItem, CategoryAdapter.CategoryViewHolder>(
        DefaultDiffCallback()
    ) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementCategoryBinding.inflate(inflater, viewGroup, false)

        return CategoryViewHolder(binding.root)
    }

    inner class CategoryViewHolder(view: View) :
        BaseViewHolder<CategoryItem>(DataBindingUtil.bind(view)!!) {

        override fun onBind(item: CategoryItem) {
            super.onBind(item)

            (binding as ElementCategoryBinding).run {
                elementCategoryChipMain.text = item.name
            }
        }
    }
}