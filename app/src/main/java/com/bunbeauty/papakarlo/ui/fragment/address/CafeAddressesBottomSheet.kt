package com.bunbeauty.papakarlo.ui.fragment.address

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.CAFE_ADDRESS_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_CAFE_ADDRESS_KEY
import com.bunbeauty.papakarlo.databinding.BottomSheetCafeAddressesBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.address.CafeAddressesViewModel
import com.bunbeauty.papakarlo.ui.adapter.AddressAdapter
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheet
import com.bunbeauty.papakarlo.ui.custom.MarginItemDecoration
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CafeAddressesBottomSheet : BaseBottomSheet<BottomSheetCafeAddressesBinding>() {

    @Inject
    lateinit var addressAdapter: AddressAdapter

    @Inject
    lateinit var marginItemDecoration: MarginItemDecoration

    override val viewModel: CafeAddressesViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cafeAddressList.onEach { cafeAddressList ->
            addressAdapter.submitList(cafeAddressList)
        }.startedLaunch()
        addressAdapter.setOnItemClickListener { address ->
            setFragmentResult(
                CAFE_ADDRESS_REQUEST_KEY,
                bundleOf(RESULT_CAFE_ADDRESS_KEY to address.uuid)
            )
            viewModel.goBack()
        }
        viewDataBinding.bottomSheetCafeAddressesRvResult.adapter = addressAdapter
        viewDataBinding.bottomSheetCafeAddressesRvResult.addItemDecoration(marginItemDecoration)
    }
}