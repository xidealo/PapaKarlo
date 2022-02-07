package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.decorator.MarginItemVerticalDecoration
import com.bunbeauty.papakarlo.databinding.FragmentCafeListBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CafeListFragment : BaseFragment(R.layout.fragment_cafe_list) {

    val cafeAdapter: CafeAdapter by inject()

    val marginItemVerticalDecoration: MarginItemVerticalDecoration by inject()

    override val viewBinding by viewBinding(FragmentCafeListBinding::bind) { viewBinding: FragmentCafeListBinding ->
        viewBinding.fragmentCafeListRvCafeList.adapter = null
    }

    override val viewModel: CafeListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentCafeListRvCafeList.addItemDecoration(marginItemVerticalDecoration)
        viewBinding.fragmentCafeListRvCafeList.adapter = cafeAdapter.apply {
            setOnItemClickListener { cafeItem ->
                viewModel.onCafeCardClicked(cafeItem)
            }
        }
        viewModel.cafeItemList.startedLaunch { cafeItemList ->
            cafeAdapter.submitList(cafeItemList)
        }
    }
}