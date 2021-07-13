package com.bunbeauty.papakarlo.ui.cafe_list

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentCafeListBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.domain.util.uri.IUriHelper
import com.bunbeauty.papakarlo.presentation.CafeListViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CafeListFragment : BarsFragment<FragmentCafeListBinding>() {

    override var layoutId = R.layout.fragment_cafe_list
    override val viewModel: CafeListViewModel by viewModels { modelFactory }

    @Inject
    lateinit var cafeAdapter: CafeAdapter

    override val isBottomBarVisible = true

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cafeAdapter.cafeListViewModel = viewModel
        cafeAdapter.onItemClickListener = { cafeAdapterModel ->
            viewModel.onCafeCardClick(cafeAdapterModel)
        }
        viewDataBinding.fragmentCafeListRvCafeList.adapter = cafeAdapter

        viewModel.cafeListFlow.onEach { cafeList ->
            cafeAdapter.submitList(cafeList)
        }.startedLaunch(lifecycle)
    }
}