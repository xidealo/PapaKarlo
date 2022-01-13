package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.bunbeauty.papakarlo.databinding.ElementCategorySectionBinding
import com.bunbeauty.papakarlo.databinding.ElementMenuProductBinding
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.ui.adapter.base.BaseListAdapter
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.DefaultDiffCallback
import com.bunbeauty.presentation.item.MenuItem
import java.lang.ref.SoftReference
import javax.inject.Inject

class MenuProductAdapter @Inject constructor() :
    BaseListAdapter<MenuItem, MenuProductAdapter.MenuProductViewHolder>(DefaultDiffCallback()) {

    private var btnItemClickListener: ((MenuItem.MenuProductItem) -> Unit)? = null

    fun setOnButtonClickListener(listener: (MenuItem.MenuProductItem) -> Unit) {
        btnItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MenuItem.CategorySectionItem -> TYPE_ONE
            is MenuItem.MenuProductItem -> TYPE_TWO
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MenuProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = when (viewType) {
            TYPE_ONE -> ElementCategorySectionBinding.inflate(inflater, viewGroup, false)
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
                    (viewBinding as? ElementCategorySectionBinding)
                        ?.elementCategorySectionTvTitle?.text = item.name
                }
                is MenuItem.MenuProductItem -> {
                    (viewBinding as? ElementMenuProductBinding)?.run {
                        elementMenuProductTvTitle.text = item.name
                        elementMenuProductTvNewPrice.text = item.newPrice
                        elementMenuProductTvOldPrice.text = item.oldPrice
                        elementMenuProductTvOldPrice.strikeOutText()
                        elementMenuProductTvOldPrice.toggleVisibility(item.oldPrice.isNotEmpty())
                        elementMenuProductIvPhoto.setPhoto(
                            item.photoReference,
                            item.photoLink
                        ) { drawable ->
                            item.photoReference = SoftReference(drawable)
                        }

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