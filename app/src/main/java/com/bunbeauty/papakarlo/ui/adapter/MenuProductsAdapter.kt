package com.bunbeauty.papakarlo.ui.adapter

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.domain.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.ElementMenuProductBinding
import com.bunbeauty.papakarlo.ui.ProductTabFragment
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.presentation.ProductTabViewModel
import javax.inject.Inject

class MenuProductsAdapter @Inject constructor(
    private val context: Context,
    private val productHelper: IProductHelper
) : BaseAdapter<MenuProductsAdapter.MenuProductViewHolder, MenuProduct>() {

    lateinit var productTabViewModel: ProductTabViewModel
    lateinit var productTabFragment: ProductTabFragment

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MenuProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementMenuProductBinding.inflate(inflater, viewGroup, false)

        return MenuProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MenuProductViewHolder, i: Int) {
        holder.setListener(itemList[i])
        holder.binding?.context = context
        holder.binding?.productHelper = productHelper
        holder.binding?.menuProduct = itemList[i]
        if (holder.binding?.elementMenuProductTvCostOld != null) {
            holder.binding.elementMenuProductTvCostOld.paintFlags =
                holder.binding.elementMenuProductTvCostOld.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
    }

    inner class MenuProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementMenuProductBinding>(view)

        fun setListener(menuProduct: MenuProduct) {
            binding?.elementMenuProductMcvMain?.setOnClickListener {
                productTabViewModel.onProductClicked(menuProduct)
            }
            binding?.elementMenuProductBtnWant?.setOnClickListener {
                productTabViewModel.addProductToCart(menuProduct)
            }
        }
    }
}