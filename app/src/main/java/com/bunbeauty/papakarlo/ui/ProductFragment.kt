package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.presentation.menu.ProductViewModel
import com.bunbeauty.papakarlo.ui.base.TopbarCartFragment
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.onEach

class ProductFragment : TopbarCartFragment<FragmentProductBinding>() {

    override val isCartVisible = true
    override val isBottomBarVisible = true

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
                        if (ProductFragmentArgs.fromBundle(requireArguments()).photo == null) {
                            Picasso.get()
                                .load(state.data?.photoLink)
                                .fit()
                                .placeholder(R.drawable.default_product)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(fragmentProductIvPhoto)

                        } else {
                            fragmentProductIvPhoto.setImageBitmap(
                                ProductFragmentArgs.fromBundle(
                                    requireArguments()
                                ).photo
                            )
                        }

                    }
                }
                else -> Unit
            }
        }.startedLaunch(viewLifecycleOwner)
        viewDataBinding.fragmentProductBtnAdd.setOnClickListener {
            viewModel.addProductToCart(ProductFragmentArgs.fromBundle(requireArguments()).menuProductUuid)
        }
    }
}