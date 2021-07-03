package com.bunbeauty.papakarlo.ui.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.bunbeauty.domain.model.adapter.CartProductAdapterModel
import com.bunbeauty.domain.model.adapter.MenuProductAdapterModel
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ElementMenuProductBinding
import com.bunbeauty.papakarlo.ui.adapter.diff_util.CartProductDiffCallback
import com.bunbeauty.papakarlo.ui.adapter.diff_util.MenuProductDiffCallback
import com.bunbeauty.papakarlo.ui.adapter.diff_util.MyDiffCallback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
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

                Picasso.get()
                    .load(item.photoLink)
                    .fit()
                    .placeholder(R.drawable.default_product)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(elementMenuProductIvPhoto)

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