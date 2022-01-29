package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.decorator.MarginItemVerticalDecoration
import com.bunbeauty.papakarlo.databinding.FragmentCafeListBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CafeListFragment : BaseFragment(R.layout.fragment_cafe_list) {

    @Inject
    lateinit var cafeAdapter: CafeAdapter

    @Inject
    lateinit var marginItemVerticalDecoration: MarginItemVerticalDecoration

    override val viewBinding by viewBinding(FragmentCafeListBinding::bind) { viewBinding: FragmentCafeListBinding ->
        viewBinding.fragmentCafeListRvCafeList.adapter = null
    }
    override val viewModel: CafeListViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentCafeListRvCafeList.addItemDecoration(marginItemVerticalDecoration)
        viewBinding.fragmentCafeListRvCafeList.adapter = cafeAdapter.apply {
            setOnItemClickListener { cafeItem ->
                viewModel.onCafeCardClicked(cafeItem)
            }
        }
        viewModel.cafeItemList.onEach { cafeItemList ->
            cafeAdapter.submitList(cafeItemList)
        }.startedLaunch()
    }
}