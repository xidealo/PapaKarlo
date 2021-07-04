package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.ProductViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.onEach

class ProductFragment : BarsFragment<FragmentProductBinding>() {

    override var layoutId = R.layout.fragment_product
    override val viewModel: ProductViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMenuProduct(ProductFragmentArgs.fromBundle(requireArguments()).menuProductUuid)
        viewModel.menuProductState.onEach { state ->
            when (state) {
                is State.Success -> {
                    with(viewDataBinding) {
                        fragmentProductTvTitle.text = state.data?.name
                        fragmentProductTvCost.text =
                            viewModel.productHelper.getMenuProductPriceString(state.data!!)
                        fragmentProductTvOldCost.text =
                            viewModel.productHelper.getMenuProductOldPriceString(state.data!!)
                        fragmentProductTvWeight.text =
                            viewModel.stringHelper.toStringWeight(state.data!!)
                        fragmentProductTvDescription.text = state.data?.description
                        Picasso.get()
                            .load(state.data?.photoLink)
                            .fit()
                            .placeholder(R.drawable.default_product)
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(fragmentProductIvPhoto)
                    }
                }
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)
    }
}