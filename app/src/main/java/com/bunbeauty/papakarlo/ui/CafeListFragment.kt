package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.databinding.FragmentCafeListBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.cafe.CafeListViewModel
import com.bunbeauty.papakarlo.ui.adapter.CafeAdapter
import com.bunbeauty.papakarlo.ui.base.TopbarCartFragment
import com.bunbeauty.papakarlo.ui.custom.MarginItemDecoration
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CafeListFragment : TopbarCartFragment<FragmentCafeListBinding>() {

    @Inject
    lateinit var cafeAdapter: CafeAdapter

    @Inject
    lateinit var marginItemDecoration: MarginItemDecoration

    override val isCartVisible = true
    override val isBottomBarVisible = true

    override val viewModel: CafeListViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.fragmentCafeListRvCafeList.addItemDecoration(marginItemDecoration)
        cafeAdapter.setOnItemClickListener { cafeItem ->
            viewModel.onCafeCardClicked(cafeItem)
        }
        viewDataBinding.fragmentCafeListRvCafeList.adapter = cafeAdapter
        viewModel.cafeItemList.onEach { cafeItemList ->
            cafeAdapter.submitList(cafeItemList)
        }.startedLaunch()
    }

    override fun onDestroyView() {
        viewDataBinding.fragmentCafeListRvCafeList.adapter = null

        super.onDestroyView()
    }
}