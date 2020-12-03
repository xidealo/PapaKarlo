package com.bunbeauty.papakarlo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.data.model.Product
import com.bunbeauty.papakarlo.databinding.ElementProductBinding
import com.bunbeauty.papakarlo.ui.ProductFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.ProductsFragment
import javax.inject.Inject

class ProductsAdapter @Inject constructor(private val context: Context) :
    BaseAdapter<ProductsAdapter.ProductViewHolder, Product>() {

    lateinit var productsFragment: ProductsFragment

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ElementProductBinding.inflate(inflater, viewGroup, false)

        return ProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, i: Int) {
        holder.setListener(itemList[i])
        holder.binding?.context = context
        holder.binding?.product = itemList[i]
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ElementProductBinding>(view)

        fun setListener(product: Product) {
            binding?.elementProductMcvMain?.setOnClickListener {
                productsFragment.parentFragmentManager.beginTransaction()
                    .replace(
                        (productsFragment.activity as MainActivity).viewDataBinding.activityProductMenuFlMain.id,
                        ProductFragment.newInstance(product)
                    )
                    .addToBackStack(ProductFragment.TAG)
                    .commit()
            }
            binding?.elementProductBtnWant?.setOnClickListener {
                (productsFragment.activity as MainActivity).viewModel.wishProductList.add(product)
                (productsFragment.activity as MainActivity).showMessage(
                    "Вы добавили ${product.name} в корзину",
                    productsFragment.viewDataBinding.fragmentProductsClMain
                )
            }
        }
    }
}