package com.bunbeauty.papakarlo.feature.menu.category

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseListAdapter
import com.bunbeauty.papakarlo.common.BaseViewHolder
import com.bunbeauty.papakarlo.databinding.ElementCategoryBinding
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider

class CategoryAdapter  constructor(private val resourcesProvider: IResourcesProvider) :
    BaseListAdapter<CategoryItem, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementCategoryBinding.inflate(inflater, viewGroup, false)

        return CategoryViewHolder(binding)
    }

    inner class CategoryViewHolder(private val elementCategoryBinding: ElementCategoryBinding) :
        BaseViewHolder<CategoryItem>(elementCategoryBinding) {

        override fun onBind(item: CategoryItem) {
            super.onBind(item)

            elementCategoryBinding.run {
                elementCategoryChipMain.text = item.name
                updateSelected(item)
            }
        }

        override fun onBind(item: CategoryItem, payloads: List<Any>) {
            super.onBind(item, payloads)

            if (payloads.last() as Boolean) {
                elementCategoryBinding.updateSelected(item)
            }
        }

        private fun ElementCategoryBinding.updateSelected(item: CategoryItem) {
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