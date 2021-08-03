package com.bunbeauty.papakarlo.ui.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import coil.imageLoader
import coil.request.ImageRequest
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ElementMenuProductBinding
import com.bunbeauty.papakarlo.ui.adapter.diff_util.MenuProductDiffCallback
import com.bunbeauty.presentation.view_model.base.adapter.MenuProductItem
import java.lang.ref.SoftReference
import javax.inject.Inject

class MenuProductsAdapter @Inject constructor() :
    ListAdapter<MenuProductItem, BaseViewHolder<ViewBinding, MenuProductItem>>(
        MenuProductDiffCallback()
    ) {

    var onItemClickListener: ((MenuProductItem) -> Unit)? = null
    var btnItemClickListener: ((MenuProductItem) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MenuProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementMenuProductBinding.inflate(inflater, viewGroup, false)

        return MenuProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, MenuProductItem>,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    inner class MenuProductViewHolder(view: View) :
        BaseViewHolder<ElementMenuProductBinding, MenuProductItem>(
            DataBindingUtil.bind(view)!!
        ) {

        override fun onBind(item: MenuProductItem) {
            super.onBind(item)
            with(binding) {
                elementMenuProductTvTitle.text = item.name
                elementMenuProductTvCost.text = item.discountCost
                elementMenuProductTvCostOld.text = item.cost

                if (item.photoNotWeak.get() == null) {
                    val imageLoader = elementMenuProductIvPhoto.context.imageLoader

                    val request = ImageRequest.Builder(elementMenuProductIvPhoto.context)
                        .data(item.photoLink)
                        .target { drawable ->
                            item.photoNotWeak = SoftReference(drawable)
                            elementMenuProductIvPhoto.setImageDrawable(drawable)
                        }
                        .placeholder(R.drawable.default_product)
                        .build()
                    imageLoader.enqueue(request)
                } else {
                    elementMenuProductIvPhoto.setImageDrawable(item.photoNotWeak.get())
                }

                elementMenuProductTvCostOld.paintFlags =
                    elementMenuProductTvCostOld.paintFlags or STRIKE_THRU_TEXT_FLAG
                elementMenuProductMcvMain.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
                elementMenuProductBtnWant.setOnClickListener {
                    btnItemClickListener?.invoke(item)
                }
            }

        }
    }
}