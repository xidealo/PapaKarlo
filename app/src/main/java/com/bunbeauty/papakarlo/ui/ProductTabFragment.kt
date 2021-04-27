package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.common.Constants.PRODUCT_CODE
import com.bunbeauty.common.extensions.gone
import com.bunbeauty.common.extensions.visible
import com.bunbeauty.common.State
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentProductsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.ProductTabViewModel
import com.bunbeauty.papakarlo.ui.adapter.MenuProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductTabFragment : BaseFragment<FragmentProductsBinding>() {

    override var layoutId = R.layout.fragment_products
    override val viewModel: ProductTabViewModel by viewModels { modelFactory }

    @Inject
    lateinit var menuProductsAdapter: MenuProductsAdapter

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        val productCode = requireArguments().getParcelable<ProductCode>(PRODUCT_CODE)!!
        viewModel.getMenuProductList(productCode)
        viewModel.productListState.onEach { state ->
            when (state) {
                is State.Loading -> {
                    viewDataBinding.fragmentProductsTvEmpty.gone()
                    viewDataBinding.fragmentProductsRvResult.gone()
                    viewDataBinding.activityMainPbLoading.visible()
                }
                is State.Success -> {
                    viewDataBinding.fragmentProductsTvEmpty.gone()
                    viewDataBinding.fragmentProductsRvResult.visible()
                    viewDataBinding.activityMainPbLoading.gone()

                    menuProductsAdapter.setItemList(state.data)
                    viewDataBinding.fragmentProductsRvResult.smoothScrollToPosition(0)
                }
                is State.Empty -> {
                    viewDataBinding.fragmentProductsTvEmpty.visible()
                    viewDataBinding.fragmentProductsRvResult.gone()
                    viewDataBinding.activityMainPbLoading.gone()
                }
                is State.Error -> {
                }
            }
        }.launchWhenStarted(lifecycleScope)
    }

    private fun setupRecyclerView() {
        menuProductsAdapter.productTabViewModel = viewModel
        menuProductsAdapter.productTabFragment = this
        viewDataBinding.fragmentProductsRvResult.adapter = menuProductsAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(productCode: ProductCode) =
            ProductTabFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PRODUCT_CODE, productCode)
                }
            }
    }

}