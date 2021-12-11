package com.bunbeauty.papakarlo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bunbeauty.papakarlo.databinding.ElementMenuProductBinding
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.ui.adapter.base.BaseListAdapter
import com.bunbeauty.papakarlo.ui.adapter.base.BaseViewHolder
import com.bunbeauty.papakarlo.ui.adapter.diff_util.DefaultDiffCallback
import com.bunbeauty.presentation.item.MenuProductItem
import java.lang.ref.SoftReference
import javax.inject.Inject

class MenuProductsAdapter @Inject constructor() :
    BaseListAdapter<MenuProductItem, ElementMenuProductBinding, MenuProductsAdapter.MenuProductViewHolder>(
        DefaultDiffCallback()
    ) {

    private var btnItemClickListener: ((MenuProductItem) -> Unit)? = null

    fun setOnButtonClickListener(listener: (MenuProductItem) -> Unit) {
        btnItemClickListener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MenuProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementMenuProductBinding.inflate(inflater, viewGroup, false)

        return MenuProductViewHolder(binding.root)
    }

    inner class MenuProductViewHolder(view: View) :
        BaseViewHolder<ElementMenuProductBinding, MenuProductItem>(
            DataBindingUtil.bind(view)!!
        ) {

        override fun onBind(item: MenuProductItem) {
            super.onBind(item)

            binding.run {
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