package com.bunbeauty.papakarlo.feature.menu.menu_product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.bunbeauty.papakarlo.common.BaseListAdapter
import com.bunbeauty.papakarlo.common.BaseViewHolder
import com.bunbeauty.papakarlo.common.DefaultDiffCallback
import com.bunbeauty.papakarlo.databinding.ElementCategoryTitleBinding
import com.bunbeauty.papakarlo.databinding.ElementMenuProductBinding
import com.bunbeauty.papakarlo.extensions.setPhoto
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.feature.menu.MenuItem

class MenuProductAdapter:
    BaseListAdapter<MenuItem, MenuProductAdapter.MenuProductViewHolder>(DefaultDiffCallback()) {

    private var btnItemClickListener: ((MenuItem.MenuProductItemModel) -> Unit)? = null

    fun setOnButtonClickListener(listener: (MenuItem.MenuProductItemModel) -> Unit) {
        btnItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MenuItem.CategorySectionItem -> TYPE_ONE
            is MenuItem.MenuProductItemModel -> TYPE_TWO
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MenuProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = when (viewType) {
            TYPE_ONE -> ElementCategoryTitleBinding.inflate(inflater, viewGroup, false)
            TYPE_TWO -> ElementMenuProductBinding.inflate(inflater, viewGroup, false)
            else -> throw Exception("The type has to be ONE or TWO")
        }

        return MenuProductViewHolder(binding)
    }

    companion object {
        private const val TYPE_ONE = 1
        private const val TYPE_TWO = 2
    }

    inner class MenuProductViewHolder(private val viewBinding: ViewBinding) :
        BaseViewHolder<MenuItem>(viewBinding) {

        override fun onBind(item: MenuItem) {
            super.onBind(item)

            when (item) {
                is MenuItem.CategorySectionItem -> {
                    (viewBinding as? ElementCategoryTitleBinding)?.elementCategoryTitleTvName?.text =
                        item.name
                }
                is MenuItem.MenuProductItemModel -> {
                    (viewBinding as? ElementMenuProductBinding)?.run {
                        elementMenuProductTvTitle.text = item.name
                        elementMenuProductTvNewPrice.text = item.newPrice
                        elementMenuProductTvOldPrice.text = item.oldPrice
                        elementMenuProductTvOldPrice.strikeOutText()
                        elementMenuProductTvOldPrice.toggleVisibility(!item.oldPrice.isNullOrEmpty())
                        elementMenuProductIvPhoto.setPhoto(item.photoLink)

                        elementMenuProductMcvMain.setOnClickListener {
                            onItemClicked(item)
                        }
                        elementMenuProductBtnWant.setOnClickListener {
                            btnItemClickListener?.invoke(item)
                        }
                    }
                }
            }
        }
    }
}