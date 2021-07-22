package com.bunbeauty.papakarlo.ui.adapter

import android.graphics.Bitmap
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ElementMenuProductBinding
import com.bunbeauty.papakarlo.ui.adapter.diff_util.MenuProductDiffCallback
import com.bunbeauty.presentation.view_model.base.adapter.MenuProductItem
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
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

                if (item.photo.get() == null) {
                    val target = object : Target {
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            if (bitmap != null) {
                                item.photo = SoftReference(bitmap)
                                elementMenuProductIvPhoto.setImageBitmap(bitmap)
                            }
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            elementMenuProductIvPhoto.setImageDrawable(placeHolderDrawable)
                        }
                    }
                    elementMenuProductIvPhoto.tag = target
                    Picasso.get()
                        .load(item.photoLink)
                        .placeholder(R.drawable.default_product)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(target)
                } else {
                    elementMenuProductIvPhoto.setImageBitmap(item.photo.get())
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