package com.bunbeauty.papakarlo.ui.fragment.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.PRODUCT_CODE
import com.bunbeauty.domain.enums.ProductCode
import com.bunbeauty.papakarlo.databinding.FragmentProductsBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.menu.ProductTabViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.ui.adapter.MenuProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.custom.MarginItemDecoration
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductTabFragment : BaseFragment<FragmentProductsBinding>() {

    override val viewModel: ProductTabViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var menuProductsAdapter: MenuProductsAdapter

    @Inject
    lateinit var marginItemDecoration: MarginItemDecoration

    private val productCode: ProductCode by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
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

                    viewDataBinding.fragmentProductsRvResult.postDelayed(
                        {
                            menuProductsAdapter.submitList(state.data)
                        },
                        1
                    )
                }
                is State.Empty -> {
                    viewDataBinding.fragmentProductsTvEmpty.visible()
                    viewDataBinding.fragmentProductsRvResult.gone()
                    viewDataBinding.activityMainPbLoading.gone()
                }
            }
        }.startedLaunch(viewLifecycleOwner)
    }

    private fun setupRecyclerView() {
        viewDataBinding.fragmentProductsRvResult.addItemDecoration(marginItemDecoration)
        viewDataBinding.fragmentProductsRvResult.adapter = menuProductsAdapter

        menuProductsAdapter.setOnItemClickListener { menuProductItem ->
            viewModel.onProductClicked(menuProductItem)
        }
        menuProductsAdapter.setOnButtonClickListener { menuProductItem ->
            viewModel.addProductToCart(menuProductItem.uuid)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(productCode: ProductCode) = ProductTabFragment().apply {
            arguments = Bundle().apply {
                putParcelable(PRODUCT_CODE, productCode)
            }
        }
    }

    override fun onDestroyView() {
        viewDataBinding.fragmentProductsRvResult.adapter = null
        viewDataBinding.fragmentProductsRvResult.itemAnimator = null

        super.onDestroyView()
    }
}