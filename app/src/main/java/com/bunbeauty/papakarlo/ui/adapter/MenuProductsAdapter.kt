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
import com.bunbeauty.domain.model.adapter.MenuProductAdapterModel
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ElementMenuProductBinding
import com.bunbeauty.papakarlo.ui.adapter.diff_util.MenuProductDiffCallback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import javax.inject.Inject

class MenuProductsAdapter @Inject constructor() :
    ListAdapter<MenuProductAdapterModel, BaseViewHolder<ViewBinding, MenuProductAdapterModel>>(
        MenuProductDiffCallback()
    ) {

    var onItemClickListener: ((MenuProductAdapterModel) -> Unit)? = null
    var btnItemClickListener: ((MenuProductAdapterModel) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MenuProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementMenuProductBinding.inflate(inflater, viewGroup, false)

        return MenuProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, MenuProductAdapterModel>,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    inner class MenuProductViewHolder(view: View) :
        BaseViewHolder<ElementMenuProductBinding, MenuProductAdapterModel>(
            DataBindingUtil.bind(view)!!
        ) {

        override fun onBind(item: MenuProductAdapterModel) {
            super.onBind(item)
            with(binding) {
                elementMenuProductTvTitle.text = item.name
                elementMenuProductTvCost.text = item.discountCost
                elementMenuProductTvCostOld.text = item.cost

                if(item.photo == null){
                    val target = object : Target {
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            if (bitmap != null) {
                                item.photo = bitmap
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
                        .fit()
                        .placeholder(R.drawable.default_product)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(target)
                }else{
                    elementMenuProductIvPhoto.setImageBitmap(item.photo)
                }
       /*         Picasso.get()
                    .load()
                    .fit()
                    .placeholder(R.drawable.default_product)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(elementMenuProductIvPhoto)
*/
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