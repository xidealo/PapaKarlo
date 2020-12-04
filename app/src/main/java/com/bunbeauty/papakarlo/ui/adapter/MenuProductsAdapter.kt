package com.bunbeauty.papakarlo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.ElementMenuProductBinding
import com.bunbeauty.papakarlo.ui.ProductFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.ProductsFragment
import javax.inject.Inject

class MenuProductsAdapter @Inject constructor(private val context: Context) :
    BaseAdapter<MenuProductsAdapter.MenuProductViewHolder, MenuProduct>() {

    lateinit var productsFragment: ProductsFragment

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MenuProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementMenuProductBinding.inflate(inflater, viewGroup, false)

        return MenuProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MenuProductViewHolder, i: Int) {
        holder.setListener(itemList[i])
        holder.binding?.context = context
        holder.binding?.menuProduct = itemList[i]
    }

    inner class MenuProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementMenuProductBinding>(view)

        fun setListener(menuProduct: MenuProduct) {
            binding?.elementMenuProductMcvMain?.setOnClickListener {
                productsFragment.parentFragmentManager.beginTransaction()
                    .replace(
                        (productsFragment.activity as MainActivity).viewDataBinding.activityProductMenuFlMain.id,
                        ProductFragment.newInstance(menuProduct)
                    )
                    .addToBackStack(ProductFragment.TAG)
                    .commit()
            }
            binding?.elementMenuProductBtnWant?.setOnClickListener {
                (productsFragment.activity as MainActivity).viewModel.addCartProduct(menuProduct)
                (productsFragment.activity as MainActivity).showMessage(
                    "Вы добавили ${menuProduct.name} в корзину",
                    productsFragment.viewDataBinding.fragmentProductsClMain
                )
            }
        }
    }
}