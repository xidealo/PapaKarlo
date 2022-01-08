package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ElementCategoryBinding
import com.bunbeauty.papakarlo.ui.adapter.base.BaseListAdapter
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.CategoryDiffCallback
import com.bunbeauty.presentation.item.CategoryItem
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import javax.inject.Inject

class CategoryAdapter @Inject constructor(private val resourcesProvider: IResourcesProvider) :
    BaseListAdapter<CategoryItem, CategoryAdapter.CategoryViewHolder>(
        CategoryDiffCallback()
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
                updateSelected(item)
            }
        }

        override fun onBind(item: CategoryItem, payloads: List<Any>) {
            super.onBind(item, payloads)

            if (payloads.last() as Boolean) {
                (binding as ElementCategoryBinding).updateSelected(item)
            }
        }

        private fun ElementCategoryBinding.updateSelected(item: CategoryItem) {
            (binding as ElementCategoryBinding).run {
                if (item.isSelected) {
                    elementCategoryChipMain.isClickable = false
                    elementCategoryChipMain.chipBackgroundColor =
                        resourcesProvider.getColorStateListByAttr(R.attr.colorPrimary)
                    elementCategoryChipMain.setTextColor(resourcesProvider.getColorStateListByAttr(R.attr.colorOnPrimary))
                } else {
                    elementCategoryChipMain.isClickable = true
                    elementCategoryChipMain.chipBackgroundColor =
                        resourcesProvider.getColorStateListByAttr(R.attr.colorSurface)
                    elementCategoryChipMain.setTextColor(resourcesProvider.getColorStateListByAttr(R.attr.colorOnSurface))
                    elementCategoryChipMain.setOnClickListener {
                        onItemClicked(item)
                    }
                }
            }
        }
    }
}